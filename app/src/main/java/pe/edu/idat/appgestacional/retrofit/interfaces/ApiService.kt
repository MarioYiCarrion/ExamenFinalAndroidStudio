package pe.edu.idat.appgestacional.retrofit.interfaces
import pe.edu.idat.appgestacional.retrofit.response.CitaResponse
import pe.edu.idat.appgestacional.retrofit.response.MedicoResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("medicos")
    suspend fun obtenerMedicos(): MedicoResponse}

    @GET("citas")
    fun listarCitas(): Call<CitaResponse>
}

