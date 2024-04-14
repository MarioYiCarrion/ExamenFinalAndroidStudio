package pe.edu.idat.appgestacional.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.idat.appgestacional.databinding.FragmentCitasBinding
import pe.edu.idat.appgestacional.retrofit.CitaResult
import pe.edu.idat.appgestacional.retrofit.interfaces.ApiService
import pe.edu.idat.appgestacional.retrofit.response.CitaResponse
import pe.edu.idat.appgestacional.util.adapters.citasAdapter
import pe.edu.idat.appgestacional.util.citas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CitasFragment : Fragment() {

    private var _binding: FragmentCitasBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiRetrofit: Retrofit
    private lateinit var citaAdapter: citasAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentCitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val adapter = citasAdapter()
        citaAdapter = citasAdapter()
        apiRetrofit = Retrofit.Builder()
            .baseUrl("https://nodejs-mysql-restapi-test-production-895d.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        binding.rvcitas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvcitas.adapter = citaAdapter

        listaCitas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listaCitas(){
        val lista = ArrayList<citas>()

        val service = apiRetrofit.create(ApiService::class.java)
        val citaResponse =service.listarCitas()

        citaResponse.enqueue(object : Callback<CitaResponse> {
            override fun onResponse(call: Call<CitaResponse>, response: Response<CitaResponse>) {
                citaAdapter.cargarCitas(response.body()!!.results)
            }

            override fun onFailure(call: Call<CitaResponse>, t: Throwable) {

            }

        })

    }

}