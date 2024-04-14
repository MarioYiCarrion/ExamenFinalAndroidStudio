package pe.edu.idat.appgestacional.retrofit

import com.google.gson.annotations.SerializedName

data class SeguimientoResult (
    @SerializedName("id") val id: Int,
    @SerializedName("fechaCita") val fechaCita: String,
    @SerializedName("ultimaRegla") val ultimaRegla: String,
    @SerializedName("fpp") val fpp: String,
    @SerializedName("semanaGestacion") val semanaGestacion: String,
    @SerializedName("citaMedica") val citaMedica: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("paciente") val paciente: String
)