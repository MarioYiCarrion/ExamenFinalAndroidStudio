package pe.edu.idat.appgestacional.retrofit.response

import com.google.gson.annotations.SerializedName
import pe.edu.idat.appgestacional.retrofit.MedicoResult

data class MedicoResponse(
    @SerializedName("results") val resultados: List<MedicoResult>
)
