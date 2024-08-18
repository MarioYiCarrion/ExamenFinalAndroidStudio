package pe.edu.idat.appgestacional.View.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.idat.appgestacional.Model.util.HistorialMedicionesP
import pe.edu.idat.appgestacional.Model.util.adapters.historialMedicionesAdapter
import pe.edu.idat.appgestacional.ViewModel.retrofit.interfaces.ApiService
import pe.edu.idat.appgestacional.ViewModel.retrofit.response.medicionesResponse
import pe.edu.idat.appgestacional.databinding.FragmentHistorialMedicionesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HistorialMedicionesFragment : Fragment() {
    private var _binding : FragmentHistorialMedicionesBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiRetrofit: Retrofit
    private lateinit var historialMedicionesAdapter: historialMedicionesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistorialMedicionesBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historialMedicionesAdapter = historialMedicionesAdapter()
        apiRetrofit = Retrofit.Builder()
            .baseUrl("https://nodejs-mysql-restapi-test-production-895d.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        binding.rvhistorialmediciones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvhistorialmediciones.adapter = historialMedicionesAdapter

        listaHistorialMedicones()
    }

    private fun listaHistorialMedicones() {
        val lista = ArrayList<HistorialMedicionesP>()

        val service = apiRetrofit.create(ApiService::class.java)
        val medicionesResponse = service.listarMediciones()

        medicionesResponse.enqueue(object : Callback<medicionesResponse> {

            override fun onResponse(
                call: Call<medicionesResponse>, response: Response<medicionesResponse>
            ) {
                historialMedicionesAdapter.cargarHistorialMediciones(response.body()!!.results)
            }

            override fun onFailure(call: Call<medicionesResponse>, t: Throwable) {

            }

        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}