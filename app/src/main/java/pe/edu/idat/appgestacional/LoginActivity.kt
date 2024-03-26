package pe.edu.idat.appgestacional

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button
    private lateinit var checkBoxRemember: CheckBox
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth:FirebaseAuth

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
        val user: String=editTextUsername.text.toString()
        val password: String=editTextPassword.text.toString()

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            auth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this){
                    task->
                    if(task.isSuccessful){
                        action()
                    }else{
                        Toast.makeText(this, "Error en la autenticacion", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }

    private fun action(){
        startActivity(Intent(this, MainActivity::class.java))
    }


}


