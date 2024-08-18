package pe.edu.idat.appgestacional.View.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appgestacional.Model.util.Actualizacion
import pe.edu.idat.appgestacional.Model.util.adapters.ActualizacionAdapter
import pe.edu.idat.appgestacional.R

class RecordatoriosyAlertasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActualizacionAdapter
    private lateinit var listaActualizaciones: List<Actualizacion>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recordatoriosy_alertas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewActualizaciones)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inicializa la lista de actualizaciones
        listaActualizaciones = listOf(
            Actualizacion("27/08/2024","Mejoras en la Seguridad del Sistema", "Se han implementado nuevas medidas de seguridad para proteger tus datos y mantener tu información segura."),
            Actualizacion("15/08/2024","Optimización del Rendimiento", "Hemos realizado mejoras en la velocidad de carga y la estabilidad de la aplicación para una experiencia más fluida."),
            Actualizacion("05/08/2024","Nuevas Funcionalidades Agregadas", "Ahora puedes personalizar tus notificaciones y acceder a un nuevo panel de control para gestionar tus configuraciones."),
            Actualizacion("15/07/2024","Corrección de Errores","Se han corregido varios errores reportados por los usuarios para mejorar la estabilidad de la aplicación."),
            Actualizacion("05/06/2024","Actualización de la Interfaz de Usuario","La interfaz ha sido renovada para ofrecer un diseño más moderno y fácil de usar.")
            // Añade más actualizaciones aquí
        )

        adapter = ActualizacionAdapter(listaActualizaciones)
        recyclerView.adapter = adapter
    }
}
