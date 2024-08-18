package pe.edu.idat.appgestacional.Model.util.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appgestacional.ViewModel.retrofit.SeguimientoResult
import pe.edu.idat.appgestacional.databinding.ItemHistorialBinding

class historialAdapter(): RecyclerView.Adapter<historialAdapter.ViewHolder>() {

    private var listaHistorial = ArrayList<SeguimientoResult>()

    inner class ViewHolder(val binding: ItemHistorialBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistorialBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = listaHistorial.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(listaHistorial[position]){
                binding.tvcitahistorial.text = citaMedica
                binding.tvfechahistorialcita.text = fechaCita
                binding.tvnombrehistorial.text = userId
                binding.tvsemanahistorial.text = semanaGestacion
                binding.cvhistorialcitas.setOnClickListener(android.view.View.OnClickListener {
                    android.widget.Toast.makeText(itemView.context, "Cita Elegida: $fechaCita", android.widget.Toast.LENGTH_LONG)
                        .show()
                })
            }
        }
    }

    fun cargarHistorial(lista: List<SeguimientoResult>){
        listaHistorial.addAll(lista)
        notifyDataSetChanged()
    }

}