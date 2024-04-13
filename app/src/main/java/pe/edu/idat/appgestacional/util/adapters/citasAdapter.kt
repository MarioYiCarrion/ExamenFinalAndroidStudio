package pe.edu.idat.appgestacional.util.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appgestacional.databinding.ItemcitasBinding
import pe.edu.idat.appgestacional.retrofit.CitaResult
import pe.edu.idat.appgestacional.util.citas

class citasAdapter() : RecyclerView.Adapter<citasAdapter.ViewHolder>(){

    private var listaCitas=ArrayList<CitaResult>()

        inner class ViewHolder(val binding: ItemcitasBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemcitasBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() =listaCitas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(listaCitas[position]){
                binding.tvfechacita.text = fechaCita
                binding.tvcitanombre.text= paciente
                binding.tvcitaespecialidad.text = especialidad
                binding.tvcitamedico.text= medico
                binding.tvcitaclinica.text = sede
                binding.cvcitasvista.setOnClickListener(View.OnClickListener {
                    Toast.makeText(itemView.context, "Cita Elegida: $fechaCita", Toast.LENGTH_LONG)
                        .show()
                })
            }
        }
    }

    fun cargarCitas(lista: List<CitaResult>){
        listaCitas.addAll(lista)
        notifyDataSetChanged()
    }
}
