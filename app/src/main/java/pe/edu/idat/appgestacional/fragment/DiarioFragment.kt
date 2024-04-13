package pe.edu.idat.appgestacional.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
import pe.edu.idat.appgestacional.util.bdclases.Diario
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DiarioFragment : Fragment() {

    private lateinit var tvdiariofecha: TextView
    private lateinit var etdiariosintomas: EditText
    private lateinit var btdiarioguardar: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_diario, container, false)

        tvdiariofecha = view.findViewById(R.id.tvdiariofecha)
        etdiariosintomas = view.findViewById(R.id.etdiariosintomas)
        btdiarioguardar = view.findViewById(R.id.btdiarioguardar)

        val currentDate = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechahoy = sdf.format(currentDate.time)

        tvdiariofecha.setText(fechahoy)

        btdiarioguardar.setOnClickListener{
            guardarDiario()
            registrarDiarios()
        }

        databaseReference = FirebaseDatabase.getInstance().reference.child("diarioSintomas")

        return view
    }

    private fun guardarDiario(){
        val fechaRegistro = tvdiariofecha.text.toString()
        val sintomas = etdiariosintomas.text.toString()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if(userId != null){
            val diarioSintomas = Diario(fechaRegistro, sintomas, userId)

            val diarioSintomasKey = databaseReference.push().key

            if(diarioSintomasKey != null){
                databaseReference.child(diarioSintomasKey).setValue(diarioSintomas)
                    .addOnSuccessListener{
                        Snackbar.make(requireView(), "Sintomas Guardados Correctamente", Snackbar.LENGTH_LONG).show()
                    }
                    .addOnFailureListener{
                        Snackbar.make(requireView(), "Error al guardar Sintomas", Snackbar.LENGTH_LONG).show()
                    }
            }
        }else{
            Snackbar.make(requireView(), "Usuario no autenticado", Snackbar.LENGTH_LONG).show()
        }


    }

    private fun registrarDiarios() {
        val fechaRegistroDiario = tvdiariofecha.text.toString()
        val sintomas = etdiariosintomas.text.toString()
        var userId = ""

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("User")

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val email = currentUser.email
            userId = email ?: ""
        }

        // Verificar que los campos no estén vacíos
        if (fechaRegistroDiario.isNotBlank() && sintomas.isNotBlank() ) {

            val seguimiento = JSONObject().apply {
                put("id", id)
                put("fechaRegistro", fechaRegistroDiario)
                put("sintomas", sintomas)
                put("userId", userId)
            }


            GlobalScope.launch(Dispatchers.IO) {
                val url = URL("https://nodejs-mysql-restapi-test-production-895d.up.railway.app/api/diarios")
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
                            Toast.makeText(requireContext(), "Sintomas Guardadas Correctamente", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Error al Guardar los Sintomas: $responseCode", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Error al registrar los sintomas: ${e.message}", Toast.LENGTH_LONG).show()
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
