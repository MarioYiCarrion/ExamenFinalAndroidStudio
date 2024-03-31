package pe.edu.idat.appgestacional.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import pe.edu.idat.appgestacional.R

class ConsejosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_consejos, container, false)

        val spinner = view.findViewById<Spinner>(R.id.spinnerOptions)

        // Obtener las opciones del array de recursos
        val options = resources.getStringArray(R.array.options_array)

        // Crear un adaptador para el Spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Establecer el adaptador en el Spinner
        spinner.adapter = adapter

        // Configurar un listener para detectar la selección del Spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Obtener la opción seleccionada
                val selectedOption = options[position]

                // Mostrar la imagen correspondiente a la opción seleccionada
                showImageForOption(selectedOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar el caso en que no se seleccione ninguna opción
            }
        }

        return view
    }

    private fun showImageForOption(option: String) {
        val imageView: ImageView = requireView().findViewById(R.id.imageView2)

        // Obtener el ID de la imagen según la opción seleccionada
        val imageResourceId = when (option) {
            "Mes 1" -> R.drawable.mes1
            "Mes 2" -> R.drawable.mes2
            "Mes 3" -> R.drawable.mes3
            "Mes 4" -> R.drawable.mes4
            "Mes 5" -> R.drawable.mes5
            "Mes 6" -> R.drawable.mes6
            "Mes 7" -> R.drawable.mes7
            "Mes 8" -> R.drawable.mes8
            "Mes 9" -> R.drawable.mes9
            // Agrega más casos según las opciones que tengas
            else -> R.drawable.elembarazo // Imagen por defecto si la opción no coincide
        }

        // Establecer la imagen en el ImageView
        imageView.setImageResource(imageResourceId)
    }


}
