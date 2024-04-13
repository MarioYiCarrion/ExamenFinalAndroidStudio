package pe.edu.idat.appgestacional.retrofit.response

import com.google.gson.annotations.SerializedName
import pe.edu.idat.appgestacional.retrofit.CitaResult

data class CitaResponse(
    //@SerializedName("results") val resultados: List<CitaResult>
    val results: List<CitaResult>
)
