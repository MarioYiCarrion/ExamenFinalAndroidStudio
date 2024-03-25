package pe.edu.idat.appgestacional

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.floor

class SeguimientoFragment : Fragment() {
    //pruebas
    private lateinit var ultimaReglaEditText: EditText
    private lateinit var fppTextView: TextView
    private lateinit var semanaGestacionTextView: TextView
    private lateinit var citaMedicaEditText: EditText

    private val ultimaReglaCalendar = Calendar.getInstance()
    private val todayCalendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seguimiento, container, false)

        ultimaReglaEditText = view.findViewById(R.id.ultima_regla_edit_text)
        fppTextView = view.findViewById(R.id.fpp_text_view)
        semanaGestacionTextView = view.findViewById(R.id.semana_gestacion_text_view)
        citaMedicaEditText = view.findViewById(R.id.cita_medica_edit_text)

        ultimaReglaEditText.setOnClickListener {
            mostrarDatePicker()
        }

        return view
    }

    private fun mostrarDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            ultimaReglaCalendar.set(Calendar.YEAR, year)
            ultimaReglaCalendar.set(Calendar.MONTH, monthOfYear)
            ultimaReglaCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            actualizarFechaUltimaRegla()
            calcularFPP()
            calcularSemanaGestacion()
        }

        val datePickerDialog = DatePickerDialog(
            requireContext(), dateSetListener,
            ultimaReglaCalendar.get(Calendar.YEAR),
            ultimaReglaCalendar.get(Calendar.MONTH),
            ultimaReglaCalendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun actualizarFechaUltimaRegla() {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaFormateada = sdf.format(ultimaReglaCalendar.time)
        ultimaReglaEditText.setText(fechaFormateada)
    }

    private fun calcularFPP() {
        val fppCalendar = Calendar.getInstance().apply {
            time = ultimaReglaCalendar.time
            add(Calendar.DAY_OF_YEAR, 280) // Sumar 280 días (40 semanas)
        }

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fppFormateada = sdf.format(fppCalendar.time)
        fppTextView.text = fppFormateada
    }

    private fun calcularSemanaGestacion() {
        val diffMillis = todayCalendar.timeInMillis - ultimaReglaCalendar.timeInMillis
        val diffWeeks = diffMillis / (1000 * 60 * 60 * 24 * 7)

        semanaGestacionTextView.text = "Tienes $diffWeeks Semanas de Gestacion"
    }
}


