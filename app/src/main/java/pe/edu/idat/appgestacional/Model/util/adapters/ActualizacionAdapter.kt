package pe.edu.idat.appgestacional.Model.util.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appgestacional.Model.util.Actualizacion
import pe.edu.idat.appgestacional.R

class ActualizacionAdapter(private val actualizaciones: List<Actualizacion>) :
    RecyclerView.Adapter<ActualizacionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_actualizacion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actualizacion = actualizaciones[position]
        holder.titulo.text = actualizacion.titulo
        holder.descripcion.text = actualizacion.descripcion
        holder.fechaactualizacion.text = actualizacion.fechaactualizacion
    }

    override fun getItemCount(): Int = actualizaciones.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.tvTituloActualizacion)
        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcionActualizacion)
        val fechaactualizacion: TextView = itemView.findViewById((R.id.tvFechaActualizacion))
    }
}
