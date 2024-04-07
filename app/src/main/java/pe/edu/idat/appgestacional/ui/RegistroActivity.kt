package pe.edu.idat.appgestacional.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import pe.edu.idat.appgestacional.R
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class RegistroActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextDNI: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextCelular: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_registro)

        editTextName = findViewById(R.id.editTextName)
        editTextDNI = findViewById(R.id.editTextDNI)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextCelular = findViewById(R.id.editTextCelular)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        buttonRegister = findViewById(R.id.register)
        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()

        dbReference = database.reference.child("User")

        buttonRegister.setOnClickListener {
            if (validarDatos()) {
                registrarUsuario()
                CrearNuevaCuenta()
            }
        }
    }

    private fun validarDatos(): Boolean {
        val nombre = editTextName.text.toString()
        val dni = editTextDNI.text.toString()
        val direccion = editTextDireccion.text.toString()
        val celular = editTextCelular.text.toString()
        val email = editTextEmail.text.toString()
        val clave = editTextPassword.text.toString()
        val confirmaClave = editTextConfirmPassword.text.toString()

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(dni) || TextUtils.isEmpty(direccion) ||
            TextUtils.isEmpty(celular) || TextUtils.isEmpty(email) || TextUtils.isEmpty(clave) ||
            TextUtils.isEmpty(confirmaClave)
        ) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
            return false
        }

        if (clave != confirmaClave) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun registrarUsuario() {

        val nombre = editTextName.text.toString()
        val dni = editTextDNI.text.toString()
        val direccion = editTextDireccion.text.toString()
        val celular = editTextCelular.text.toString()
        val email = editTextEmail.text.toString()
        val clave = editTextPassword.text.toString()

        // Utilizamos el email como userId
        val userId = email

        val usuario = JSONObject()
        usuario.put("userId", userId)
        usuario.put("nombre", nombre)
        usuario.put("DNI", dni)
        usuario.put("direccion", direccion)
        usuario.put("celular", celular)
        usuario.put("email", email)
        usuario.put("clave", clave)

        GlobalScope.launch(Dispatchers.IO) {
            val url = URL("https://nodejs-mysql-restapi-test-production-895d.up.railway.app/api/registros")
            val conexion = url.openConnection() as HttpURLConnection

            try {
                conexion.requestMethod = "POST"
                conexion.setRequestProperty("Content-Type", "application/json")
                conexion.setRequestProperty("Accept", "application/json")
                conexion.doOutput = true

                val outputStream = OutputStreamWriter(conexion.outputStream)
                outputStream.write(usuario.toString())
                outputStream.flush()

                if (conexion.responseCode == HttpURLConnection.HTTP_OK) {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Usuario registrado exitosamente", Toast.LENGTH_LONG).show()
                    }
                    action()
                    finish()
                } else {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Error al registrar usuario", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                conexion.disconnect()
            }
        }
    }

    private fun CrearNuevaCuenta() {
        val nombre: String = editTextName.text.toString()
        val DNI: String = editTextDNI.text.toString()
        val direccion: String = editTextDireccion.text.toString()
        val email: String = editTextEmail.text.toString()
        val celular: String = editTextCelular.text.toString()
        val clave: String = editTextPassword.text.toString()
        val confirmaClave: String = editTextConfirmPassword.text.toString()


        auth.createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    verifyEmail(user)
                    val userBD = dbReference.child(user?.uid.toString())

                    userBD.child("Nombre").setValue(nombre)
                    userBD.child("DNI").setValue(DNI)
                    userBD.child("direccion").setValue(direccion)
                    userBD.child("email").setValue(email)
                    userBD.child("celular").setValue(celular)
                    userBD.child("clave").setValue(clave)
                    action()
                    finish()
                } else {
                    Toast.makeText(this, "Error al crear la cuenta", Toast.LENGTH_LONG).show()

                }
            }
    }

    private fun action() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun verifyEmail(user: FirebaseUser?) {
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de Verificación Enviado", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error al Enviar Correo de Verificación", Toast.LENGTH_LONG).show()
                }
            }
    }
}
