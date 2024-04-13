package pe.edu.idat.appgestacional.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import pe.edu.idat.appgestacional.R
import pe.edu.idat.appgestacional.util.bdclases.Diario
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DiarioFragment : Fragment() {

    private lateinit var tvdiariofecha: TextView
    private lateinit var etdiariosintomas: EditText
    private lateinit var btdiarioguardar: Button

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
        }

        databaseReference = FirebaseDatabase.getInstance().reference.child("diarioSintomas")

        return view
    }

    private fun guardarDiario() {
        val fechaRegistro = tvdiariofecha.text.toString()
        val sintomas = etdiariosintomas.text.toString()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null && sintomas.isNotEmpty()) {
            val diarioSintomas = Diario(fechaRegistro, sintomas, userId)
            val diarioSintomasKey = databaseReference.push().key

            if (diarioSintomasKey != null) {
                databaseReference.child(diarioSintomasKey).setValue(diarioSintomas)
                    .addOnSuccessListener {
                        Snackbar.make(requireView(), "Síntomas guardados correctamente", Snackbar.LENGTH_LONG).show()
                        // Limpiar campos después de guardar
                        etdiariosintomas.text.clear()
                    }
                    .addOnFailureListener {
                        Snackbar.make(requireView(), "Error al guardar los síntomas: ${it.message}", Snackbar.LENGTH_LONG).show()
                    }
            } else {
                Snackbar.make(requireView(), "Error al generar la clave para el diario de síntomas", Snackbar.LENGTH_LONG).show()
            }
        } else {
            Snackbar.make(requireView(), "Por favor, ingrese los síntomas", Snackbar.LENGTH_LONG).show()
        }
    }

}
