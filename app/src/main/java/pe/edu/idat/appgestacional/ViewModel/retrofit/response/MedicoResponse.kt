package pe.edu.idat.appgestacional.ViewModel.retrofit.response

import com.google.gson.annotations.SerializedName
import pe.edu.idat.appgestacional.ViewModel.retrofit.MedicoResult

data class MedicoResponse(
    @SerializedName("results") val resultados: List<MedicoResult>
)
