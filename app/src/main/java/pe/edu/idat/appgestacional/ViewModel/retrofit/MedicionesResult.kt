package pe.edu.idat.appgestacional.ViewModel.retrofit

import com.google.gson.annotations.SerializedName

data class MedicionesResult(
    @SerializedName("id") val id: Int,
    @SerializedName("fecharesgistro") val fecharegistro: String,
    @SerializedName("mesEmbarazo") val mesEmbarazo: String,
    @SerializedName("temperatura") val temperatura: String,
    @SerializedName("peso") val peso: String,
    @SerializedName("altura") val altura: String,
    @SerializedName("IMC") val IMC: String,
    @SerializedName("presionArterial") val presionArterial: String,
    @SerializedName("AlturaUterina") val AlturaUterina: String,
    @SerializedName("FrecCardiaca") val FrecCardiaca: String,
    @SerializedName("MovFetal") val MovFetal: String,
    @SerializedName("edema") val edema: String,
    @SerializedName("centroAtencion") val centroAtencion: String,
    @SerializedName("userId") val userId: String
)
