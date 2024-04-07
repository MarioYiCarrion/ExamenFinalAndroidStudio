package pe.edu.idat.appgestacional.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import pe.edu.idat.appgestacional.R
import pe.edu.idat.appgestacional.util.bdclases.mediciones
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

        tvfechamedicion.setText(fechahoy)

        btnguardarmed.setOnClickListener{
            guardarMedicion()
        }

        databaseReference = FirebaseDatabase.getInstance().reference.child("medicionesPruebas")

        return view

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
}