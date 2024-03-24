package pe.edu.idat.appgestacional

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button
    private lateinit var checkBoxRemember: CheckBox
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)
        checkBoxRemember = findViewById(R.id.cbrecorda)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        // Verificar si se deben recordar las credenciales almacenadas
        val rememberMe = sharedPreferences.getBoolean("rememberMe", false)
        if (rememberMe) {
            val savedUsername = sharedPreferences.getString("username", "")
            val savedPassword = sharedPreferences.getString("password", "")
            editTextUsername.setText(savedUsername)
            editTextPassword.setText(savedPassword)
            checkBoxRemember.isChecked = true
        }

        buttonLogin.setOnClickListener { login() }
        buttonRegister.setOnClickListener {
            // Iniciar la actividad de registro
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    private fun login() {
        val username = editTextUsername.text.toString()
        val password = editTextPassword.text.toString()
        if (checkBoxRemember.isChecked) {
            // Si el checkbox está marcado, guardar las credenciales
            val editor = sharedPreferences.edit()
            editor.putBoolean("rememberMe", true)
            editor.putString("username", username)
            editor.putString("password", password)
            editor.apply()
        } else {
            // Si el checkbox no está marcado, limpiar las credenciales guardadas
            val editor = sharedPreferences.edit()
            editor.putBoolean("rememberMe", false)
            editor.putString("username", "")
            editor.putString("password", "")
            editor.apply()
        }

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


