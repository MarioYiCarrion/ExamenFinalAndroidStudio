package pe.edu.idat.appgestacional.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import pe.edu.idat.appgestacional.R

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextUsername: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button
    private lateinit var checkBoxRemember: CheckBox
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)
        checkBoxRemember = findViewById(R.id.cbrecorda)

        auth= FirebaseAuth.getInstance()

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("username", "")
        val savedPassword = sharedPreferences.getString("password", "")
        if (!savedUsername.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
            editTextUsername.setText(savedUsername)
            editTextPassword.setText(savedPassword)
            checkBoxRemember.isChecked = true
        }
    }

    fun olvideclave(view:View){
        startActivity(Intent(this, OlvidoClaveActivity::class.java))
    }

    fun iniciarsesion(view:View){
        loginUsuario()
    }

    fun registrar(view:View){
        startActivity(Intent(this, RegistroActivity::class.java))
    }

    private fun loginUsuario() {
        val user: String = editTextUsername.text.toString()
        val password: String = editTextPassword.text.toString()

        if (user.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa tu usuario y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(user, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (checkBoxRemember.isChecked) {
                        // Almacenar un indicador de "sesión iniciada" en lugar de credenciales
                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                    } else {
                        sharedPreferences.edit().remove("isLoggedIn").apply()
                    }
                    action()
                    finish()
                } else {
                    // Mostrar mensaje de error detallado
                    val exception = task.exception
                    if (exception != null) {
                        if (exception is FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this, "Credenciales inválidas. Por favor, verifica tu usuario y contraseña", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error en la autenticación: ${exception.message}", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this, "Error en la autenticación.", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    override fun onResume() {
        super.onResume()
        // Verificar si ya hay una sesión iniciada
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            action()
            finish()
        }
    }



    private fun action(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}