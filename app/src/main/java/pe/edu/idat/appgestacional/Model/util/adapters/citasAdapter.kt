package pe.edu.idat.appgestacional.Model.util.adapters

import android.content.Intent
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appgestacional.ViewModel.retrofit.CitaResult
import pe.edu.idat.appgestacional.databinding.ItemcitasBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

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
        with(holder) {
            with(listaCitas[position]) {
                binding.tvfechacita.text = fechaCita
                binding.tvcitanombre.text = paciente
                binding.tvcitaespecialidad.text = especialidad
                binding.tvcitamedico.text = medico
                binding.tvcitaclinica.text = sede
                binding.cvcitasvista.setOnClickListener {

                    val fechaCitaInMillis = convertirFechaAMillis(fechaCita)

                    // Crear un Intent para agregar evento al calendario
                    val intent = Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE, "Cita Médica")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Cita con $paciente para $especialidad")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, sede)
                        .putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, fechaCitaInMillis)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fechaCitaInMillis + 60 * 60 * 1000) // Duración de una hora

                    // Lanzar el intent para agregar el evento al calendario
                    itemView.context.startActivity(intent)

                    // Mostrar Toast
                    Toast.makeText(itemView.context, "Cita Agregada al Calendario: $fechaCita", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun cargarCitas(lista: List<CitaResult>){
        listaCitas.addAll(lista)
        notifyDataSetChanged()
    }

    private fun convertirFechaAMillis(fecha: String): Long {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = formatter.parse(fecha)
        return date?.time ?: 0
    }
}
