package pe.edu.idat.appgestacional.ViewModel.retrofit

import com.google.gson.annotations.SerializedName

data class MedicoResult(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String
)
