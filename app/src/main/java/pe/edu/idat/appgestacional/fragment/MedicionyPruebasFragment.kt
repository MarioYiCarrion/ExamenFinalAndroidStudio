package pe.edu.idat.appgestacional.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
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
import pe.edu.idat.appgestacional.util.bdclases.mediciones
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MedicionyPruebasFragment : Fragment() {

    private lateinit var tvfechamedicion: TextView
    private lateinit var spmesmedicion: Spinner
    private lateinit var ettemperaturamed: EditText
    private lateinit var etpesomed: EditText
    private lateinit var etAlturamed: EditText
    private lateinit var etIMC: EditText
    private lateinit var etpresionmed: EditText
    private lateinit var etalturauterina: EditText
    private lateinit var etfrecuenciacard: EditText
    private lateinit var spmovfetal: Spinner
    private lateinit var spedema: Spinner
    private lateinit var spcatencion: Spinner
    private lateinit var btnguardarmed: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mediciony_pruebas, container, false)

        tvfechamedicion = view.findViewById(R.id.tvfechamedicion)
        spmesmedicion = view.findViewById(R.id.spmesmedicion)
        ettemperaturamed = view.findViewById(R.id.ettemperaturamed)
        etpesomed = view.findViewById(R.id.etpesomed)
        etAlturamed = view.findViewById(R.id.etAlturamed)
        etIMC = view.findViewById(R.id.etIMC)
        etpresionmed = view.findViewById(R.id.etpresionmed)
        etalturauterina = view.findViewById(R.id.etalturauterina)
        etfrecuenciacard = view.findViewById(R.id.etfrecuenciacard)
        spmovfetal = view.findViewById(R.id.spmovfetal)
        spedema = view.findViewById(R.id.spedema)
        spcatencion = view.findViewById(R.id.spcatencion)
        btnguardarmed = view.findViewById(R.id.btnguardarmed)



        val currentDate = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechahoy = sdf.format(currentDate.time)

        tvfechamedicion.text = fechahoy

        // Agregar TextWatcher para el cálculo automático del IMC
        etAlturamed.addTextChangedListener(imcWatcher)
        etpesomed.addTextChangedListener(imcWatcher)

        btnguardarmed.setOnClickListener {
            guardarMedicion()
            registrarMediciones()
        }

        databaseReference = FirebaseDatabase.getInstance().reference.child("medicionesPruebas")

        return view
    }

    // TextWatcher para calcular el IMC automáticamente
    private val imcWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            calcularIMC()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun calcularIMC() {
        val peso = etpesomed.text.toString().toDoubleOrNull()
        val altura = etAlturamed.text.toString().toDoubleOrNull()

        if (peso != null && altura != null && altura != 0.0) {
            val imc = peso / (altura * altura)
            val imcFormateado = String.format("%.2f", imc)
            etIMC.setText(imcFormateado)
        } else {
            etIMC.setText("")
        }
    }


    private fun guardarMedicion() {
        val fecharesgistro = tvfechamedicion.text.toString()
        val mesEmbarazo = spmesmedicion.selectedItem.toString()
        val temperatura = ettemperaturamed.text.toString()
        val peso = etpesomed.text.toString()
        val altura = etAlturamed.text.toString()
        val IMC = etIMC.text.toString()
        val presionArterial = etpresionmed.text.toString()
        val AlturaUterina = etalturauterina.text.toString()
        val FrecCardiaca = etfrecuenciacard.text.toString()
        val MovFetal = spmovfetal.selectedItem.toString()
        val edema = spedema.selectedItem.toString()
        val centroAtencion = spcatencion.selectedItem.toString()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if(userId != null){
            val medicionesPruebas = mediciones(fecharesgistro,mesEmbarazo,temperatura,peso,altura
            ,IMC,presionArterial,AlturaUterina,FrecCardiaca,MovFetal,edema,centroAtencion,userId)

            val medicionesPruebasKey = databaseReference.push().key

            if(medicionesPruebasKey != null){
                databaseReference.child(medicionesPruebasKey).setValue(medicionesPruebas)
                    .addOnSuccessListener{
                        Snackbar.make(requireView(), "Mediciones Guardadas Correctamente", Snackbar.LENGTH_LONG).show()
                    }
                    .addOnFailureListener{
                        Snackbar.make(requireView(), "Error al Guardar Mediciones", Snackbar.LENGTH_LONG).show()
                    }

            }

        }else{
            Snackbar.make(requireView(), "Usuario no autenticado", Snackbar.LENGTH_LONG).show()
        }

    }

    private fun registrarMediciones() {
        val fecharesgistro = tvfechamedicion.text.toString()
        val mesEmbarazo = spmesmedicion.selectedItem.toString()
        val temperatura = ettemperaturamed.text.toString()
        val peso = etpesomed.text.toString()
        val altura = etAlturamed.text.toString()
        val IMC = etIMC.text.toString()
        val presionArterial = etpresionmed.text.toString()
        val AlturaUterina = etalturauterina.text.toString()
        val FrecCardiaca = etfrecuenciacard.text.toString()
        val MovFetal = spmovfetal.selectedItem.toString()
        val edema = spedema.selectedItem.toString()
        val centroAtencion = spcatencion.selectedItem.toString()
        var userId = ""

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("User")

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val email = currentUser.email
            userId = email ?: ""
        }

        // Verificar que los campos no estén vacíos
        if (fecharesgistro.isNotBlank() && mesEmbarazo.isNotBlank() && temperatura.isNotBlank() &&
            peso.isNotBlank() && altura.isNotBlank() && IMC.isNotBlank() && presionArterial.isNotBlank()
            && AlturaUterina.isNotBlank() && FrecCardiaca.isNotBlank() && MovFetal.isNotBlank()
            && edema.isNotBlank() && centroAtencion.isNotBlank()) {

            val seguimiento = JSONObject().apply {
                put("id", id)
                put("fecharesgistro", fecharesgistro)
                put("mesEmbarazo", mesEmbarazo)
                put("temperatura", temperatura)
                put("peso", peso)
                put("altura", altura)
                put("IMC", IMC)
                put("presionArterial", presionArterial)
                put("AlturaUterina", AlturaUterina)
                put("FrecCardiaca", FrecCardiaca)
                put("MovFetal", MovFetal)
                put("edema", edema)
                put("centroAtencion", centroAtencion)
                put("userId", userId)
            }


            GlobalScope.launch(Dispatchers.IO) {
                val url = URL("https://nodejs-mysql-restapi-test-production-895d.up.railway.app/api/mediciones")
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
                            Toast.makeText(requireContext(), "Mediciones Guardadas Correctamente", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Error al Guardar las Mediciones: $responseCode", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Error al registrar las Mediciones: ${e.message}", Toast.LENGTH_LONG).show()
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