package pe.edu.idat.appgestacional.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.idat.appgestacional.databinding.FragmentHistorialCitasBinding
import pe.edu.idat.appgestacional.retrofit.interfaces.ApiService
import pe.edu.idat.appgestacional.retrofit.response.SeguimientoResponse
import pe.edu.idat.appgestacional.util.adapters.historialAdapter
import pe.edu.idat.appgestacional.util.historial
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistorialCitasFragment : Fragment() {

    private var _binding : FragmentHistorialCitasBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiRetrofit: Retrofit
    private lateinit var historialAdapter: historialAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistorialCitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        historialAdapter = historialAdapter()
        apiRetrofit = Retrofit.Builder()
            .baseUrl("https://nodejs-mysql-restapi-test-production-895d.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        binding.rvhistorialcitas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvhistorialcitas.adapter = historialAdapter

        listaHistorial()
    }

    private fun listaHistorial() {
        val lista = ArrayList<historial>()

        val service = apiRetrofit.create(ApiService::class.java)
        val seguimientoResponse =service.listarSeguimientos()

        seguimientoResponse.enqueue(object : Callback<SeguimientoResponse> {

            override fun onResponse(call: Call<SeguimientoResponse>, response: Response<SeguimientoResponse>
            ) {
                historialAdapter.cargarHistorial(response.body()!!.results)
            }

            override fun onFailure(call: Call<SeguimientoResponse>, t: Throwable) {

            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}