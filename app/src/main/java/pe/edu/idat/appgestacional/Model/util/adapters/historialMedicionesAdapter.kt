package pe.edu.idat.appgestacional.Model.util.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appgestacional.Model.util.adapters.historialAdapter.ViewHolder
import pe.edu.idat.appgestacional.ViewModel.retrofit.MedicionesResult
import pe.edu.idat.appgestacional.databinding.ItemMedicionesBinding

class historialMedicionesAdapter(): RecyclerView.Adapter<historialMedicionesAdapter.ViewHolder>() {

    private var listaHistorialMediciones = ArrayList<MedicionesResult>()

    inner class ViewHolder(val binding: ItemMedicionesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): pe.edu.idat.appgestacional.Model.util.adapters.historialMedicionesAdapter.ViewHolder {
        val binding = ItemMedicionesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = listaHistorialMediciones.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(listaHistorialMediciones[position]){
                binding.tvfechaderegistrohistorialmed.text = fecharegistro
                binding.tvedema.text = edema
                binding.tvalturauterina.text = AlturaUterina
                binding.tvcentroatencion.text = centroAtencion
                binding.tvimchistorialmed.text = IMC
                binding.tvalturahistorialmed.text = altura
                binding.tvfrecCardiaca.text = FrecCardiaca
                binding.tvmesembarazohistorialmed.text = mesEmbarazo
                binding.tvtemperaturahistorialmed.text = temperatura
                binding.tvpesohistorialmed.text = peso
                binding.tvpresionarterialhistorialmed.text = presionArterial
                binding.tvmovFetal.text = MovFetal
                binding.cvhistorialmediciones.setOnClickListener(android.view.View.OnClickListener {
                    android.widget.Toast.makeText(itemView.context, "Cita Elegida: $fecharegistro", android.widget.Toast.LENGTH_LONG)
                        .show()
                })
            }
        }
    }

    fun cargarHistorialMediciones(lista: List<MedicionesResult>){
        listaHistorialMediciones.addAll(lista)
        notifyDataSetChanged()
    }
}