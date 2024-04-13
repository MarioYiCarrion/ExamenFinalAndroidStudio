import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import pe.edu.idat.appgestacional.R
import pe.edu.idat.appgestacional.retrofit.interfaces.ApiService
import pe.edu.idat.appgestacional.util.bdclases.Seguimiento
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class SeguimientoFragment : Fragment() {

    private lateinit var ultimaReglaEditText: EditText
    private lateinit var fppTextView: TextView
    private lateinit var semanaGestacionTextView: TextView
    private lateinit var citaMedicaEditText: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var tvfecha: TextView
    private lateinit var spmedico: Spinner

    private lateinit var databaseReference: DatabaseReference

    private val ultimaReglaCalendar = Calendar.getInstance()
    private val todayCalendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seguimiento, container, false)

        ultimaReglaEditText = view.findViewById(R.id.ultima_regla_edit_text)
        fppTextView = view.findViewById(R.id.fpp_text_view)
        semanaGestacionTextView = view.findViewById(R.id.semana_gestacion_text_view)
        citaMedicaEditText = view.findViewById(R.id.cita_medica_edit_text)
        btnRegistrar = view.findViewById(R.id.btnRegistrar)
        tvfecha = view.findViewById(R.id.tvfecha)
        spmedico = view.findViewById(R.id.spmedico)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://nodejs-mysql-restapi-test-production-895d.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.obtenerMedicos()
                val nombresMedicos = response.resultados.map { it.nombre }

                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    nombresMedicos
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spmedico.adapter = adapter
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle errors
            }
        }

        // Set current date to the EditText
        val currentDate = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaActual = sdf.format(currentDate.time)
        tvfecha.text = fechaActual

        ultimaReglaEditText.setOnClickListener {
            mostrarDatePicker()
        }

        btnRegistrar.setOnClickListener {
            guardarInformacion()
            registrarInformacion()
        }

        databaseReference = FirebaseDatabase.getInstance().reference.child("seguimiento")

        return view
    }

    private fun mostrarDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            ultimaReglaCalendar.set(Calendar.YEAR, year)
            ultimaReglaCalendar.set(Calendar.MONTH, monthOfYear)
            ultimaReglaCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            actualizarFechaUltimaRegla()
            calcularFPP()
            calcularSemanaGestacion()
        }

        val datePickerDialog = DatePickerDialog(
            requireContext(), dateSetListener,
            ultimaReglaCalendar.get(Calendar.YEAR),
            ultimaReglaCalendar.get(Calendar.MONTH),
            ultimaReglaCalendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun actualizarFechaUltimaRegla() {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaFormateada = sdf.format(ultimaReglaCalendar.time)
        ultimaReglaEditText.setText(fechaFormateada)
    }

    private fun calcularFPP() {
        val fppCalendar = Calendar.getInstance().apply {
            time = ultimaReglaCalendar.time
            add(Calendar.DAY_OF_YEAR, 280) // Add 280 days (40 weeks)
        }

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fppFormateada = sdf.format(fppCalendar.time)
        fppTextView.text = fppFormateada
    }

    private fun calcularSemanaGestacion() {
        val diffMillis = todayCalendar.timeInMillis - ultimaReglaCalendar.timeInMillis
        val diffWeeks = diffMillis / (1000 * 60 * 60 * 24 * 7)

        semanaGestacionTextView.text = "Tienes $diffWeeks Semanas de Gestacion"
    }

    private fun validarCampos(): Boolean {
        // Obtener los valores de los campos de entrada
        val fechaCita = tvfecha.text.toString()
        val ultimaRegla = ultimaReglaEditText.text.toString()
        val citaMedica = citaMedicaEditText.text.toString()

        // Verificar si algún campo está vacío
        val camposVacios = fechaCita.isBlank() || ultimaRegla.isBlank() || citaMedica.isBlank()

        // Devolver true si no hay campos vacíos, false si hay al menos uno vacío
        if (camposVacios) {
            Log.d("ValidarCampos", "Campos vacíos: fechaCita=$fechaCita, ultimaRegla=$ultimaRegla, citaMedica=$citaMedica")
        } else {
            Log.d("ValidarCampos", "Todos los campos están completos")
        }

        return !camposVacios
    }

    private fun guardarInformacion() {
        Log.d("GuardarInformacion", "Llamando a guardarInformacion()")
        if (validarCampos()) {
            Log.d("GuardarInformacion", "Campos validados, procediendo a guardar información.")

            // Obtener el ID de usuario actual
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            if (userId != null) {
                // Obtener la entrada del usuario
                val fechaCita = tvfecha.text.toString()
                val ultimaRegla = ultimaReglaEditText.text.toString()
                val fpp = fppTextView.text.toString()
                val semanaGestacion = semanaGestacionTextView.text.toString()
                val citaMedica = citaMedicaEditText.text.toString()
                val medicoSeleccionado = spmedico.selectedItem.toString()

                val seguimiento = Seguimiento(fechaCita, ultimaRegla, fpp, semanaGestacion, citaMedica, medicoSeleccionado, userId)

                val seguimientoKey = databaseReference.push().key

                if (seguimientoKey != null) {
                    databaseReference.child(seguimientoKey).setValue(seguimiento)
                        .addOnSuccessListener {
                            Snackbar.make(requireView(), "Información guardada correctamente", Snackbar.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
                            Snackbar.make(requireView(), "Error al guardar la información", Snackbar.LENGTH_LONG).show()
                        }
                }
            } else {
                Log.d("GuardarInformacion", "Usuario no autenticado, no se guarda la información.")
                Snackbar.make(requireView(), "Por favor, inicia sesión para guardar la información", Snackbar.LENGTH_LONG).show()
            }
        } else {
            Log.d("GuardarInformacion", "Al menos un campo está vacío, no se guarda la información.")
        }
    }


    private fun registrarInformacion() {
        if (validarCampos()) {
            // Get user input
            val fechaCita = tvfecha.text.toString()
            val ultimaRegla = ultimaReglaEditText.text.toString()
            val fpp = fppTextView.text.toString()
            val semanaGestacion = semanaGestacionTextView.text.toString()
            val citaMedica = citaMedicaEditText.text.toString()
            val medicoSeleccionado = "marioyicarrion@gmail.com"
            val id = ""

            val seguimiento = JSONObject().apply {
                put("id", id)
                put("fechaCita", fechaCita)
                put("ultimaRegla", ultimaRegla)
                put("fpp", fpp)
                put("semanaGestacion", semanaGestacion)
                put("citaMedica", citaMedica)
                put("userId", medicoSeleccionado)
            }

            GlobalScope.launch(Dispatchers.IO) {
                val url = URL("https://nodejs-mysql-restapi-test-production-895d.up.railway.app/api/seguimientos")
                val conexion = url.openConnection() as HttpURLConnection

                try {
                    conexion.requestMethod = "POST"
                    conexion.setRequestProperty("Content-Type", "application/json")
                    conexion.setRequestProperty("Accept", "application/json")
                    conexion.doOutput = true

                    val outputStream = OutputStreamWriter(conexion.outputStream)
                    outputStream.write(seguimiento.toString())
                    outputStream.flush()

                    val responseCode = conexion.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Usuario registrado exitosamente", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Error al registrar usuario: $responseCode", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Error al registrar usuario: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                } finally {
                    conexion.disconnect()
                }
            }
        } else {
            // Mostrar mensaje si algún campo está vacío
            Toast.makeText(requireContext(), "Por favor completa todos los campos", Toast.LENGTH_LONG).show()
        }
    }
}
