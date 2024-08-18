package pe.edu.idat.appgestacional.ViewModel.retrofit.interfaces



import pe.edu.idat.appgestacional.ViewModel.retrofit.response.CitaResponse
import pe.edu.idat.appgestacional.ViewModel.retrofit.response.MedicoResponse
import pe.edu.idat.appgestacional.ViewModel.retrofit.response.SeguimientoResponse
import pe.edu.idat.appgestacional.ViewModel.retrofit.response.medicionesResponse
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {

    @GET("medicos")
    suspend fun obtenerMedicos(): MedicoResponse

    @GET("citas")
    fun listarCitas(): Call<CitaResponse>

    @GET("seguimientos")
    fun listarSeguimientos(): Call<SeguimientoResponse>

    @GET("mediciones")
    fun listarMediciones() : Call<medicionesResponse>
}

