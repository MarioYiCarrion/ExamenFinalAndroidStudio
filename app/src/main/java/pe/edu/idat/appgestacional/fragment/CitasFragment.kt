package pe.edu.idat.appgestacional.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.idat.appgestacional.databinding.FragmentCitasBinding
import pe.edu.idat.appgestacional.util.adapters.citasAdapter
import pe.edu.idat.appgestacional.util.citas

class CitasFragment : Fragment() {

    private var _binding: FragmentCitasBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = citasAdapter(listaCitas(), requireContext())
        binding.rvcitas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvcitas.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listaCitas(): List<citas> {
        val lista = ArrayList<citas>()
        lista.add(citas("07/04/2024","Esthefania Quevedo Delgado","Ginecologia",
            "Dra. Ramirez","C.I. San Borja"))
        lista.add(citas("07/05/2024","Esthefania Quevedo Delgado","Ginecologia",
            "Dra. Ramirez","C.I. San Borja"))
        lista.add(citas("07/06/2024","Esthefania Quevedo Delgado","Ginecologia",
            "Dra. Ramirez","C.I. San Borja"))
        lista.add(citas("07/07/2024","Esthefania Quevedo Delgado","Ginecologia",
            "Dra. Ramirez","C.I. San Borja"))
        lista.add(citas("07/08/2024","Esthefania Quevedo Delgado","Ginecologia",
            "Dra. Ramirez","C.I. San Borja"))
        lista.add(citas("07/09/2024","Esthefania Quevedo Delgado","Ginecologia",
            "Dra. Ramirez","C.I. San Borja"))
        lista.add(citas("07/10/2024","Esthefania Quevedo Delgado","Ginecologia",
            "Dra. Ramirez","C.I. San Borja"))
        lista.add(citas("07/11/2024","Esthefania Quevedo Delgado","Ginecologia",
            "Dra. Ramirez","C.I. San Borja"))
        lista.add(citas("07/12/2024","Esthefania Quevedo Delgado","Ginecologia",
            "Dra. Ramirez","C.I. San Borja"))
        return lista
    }

}