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

    private fun loginUsuario(){
        val user: String = editTextUsername.text.toString()
        val password: String = editTextPassword.text.toString()

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {
            auth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        if (checkBoxRemember.isChecked) {
                            // Guardar credenciales en SharedPreferences si el CheckBox está marcado
                            val editor = sharedPreferences.edit()
                            editor.putString("username", user)
                            editor.putString("password", password)
                            editor.apply()
                        } else {
                            // Limpiar credenciales de SharedPreferences si el CheckBox no está marcado
                            val editor = sharedPreferences.edit()
                            editor.remove("username")
                            editor.remove("password")
                            editor.apply()

                        }
                        action()
                        finish()
                    } else {                        
                        Toast.makeText(this, "Error en la autenticacion", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun action(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}
