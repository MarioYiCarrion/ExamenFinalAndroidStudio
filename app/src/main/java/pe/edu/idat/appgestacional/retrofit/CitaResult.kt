package pe.edu.idat.appgestacional.retrofit

import com.google.gson.annotations.SerializedName

data class CitaResult(
    @SerializedName("id") val id: Int,
    @SerializedName("fechaCita") val fechaCita: String,
    @SerializedName("especialidad") val especialidad: String,
    @SerializedName("sede") val sede: String,
    @SerializedName("medicoId") val medicoId: Int,
    @SerializedName("medico") val medico: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("paciente") val paciente: String
)
