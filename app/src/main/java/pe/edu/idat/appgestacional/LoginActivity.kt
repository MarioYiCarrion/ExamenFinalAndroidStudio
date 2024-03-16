package pe.edu.idat.appgestacional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener { login() }
    }

    private fun login() {
        val username = editTextUsername!!.text.toString()
        val password = editTextPassword!!.text.toString()
        if (username == "MarioYi" && password == "123456") {
            // Autenticación exitosa, iniciar MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Finaliza LoginActivity para que no pueda volver atrás
        } else {
            // Autenticación fallida
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
}