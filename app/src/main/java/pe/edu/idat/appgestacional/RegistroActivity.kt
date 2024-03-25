package pe.edu.idat.appgestacional

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class RegistroActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private  lateinit var editTextDNI: EditText
    private  lateinit var editTextDireccion: EditText
    private  lateinit var editTextCelular: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Inicialización de vistas
        editTextName = findViewById(R.id.editTextName)
        editTextDNI = findViewById(R.id.editTextDNI)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextCelular = findViewById(R.id.editTextCelular)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        buttonRegister = findViewById(R.id.buttonRegister)

        // Configuración del listener de clic en el botón de registro
        buttonRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val name = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val confirmPassword = editTextConfirmPassword.text.toString().trim()

        // Validación de campos vacíos
        if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() ||
            confirmPassword.isEmpty()) {
            // Mostrar mensaje de error si algún campo está vacío
            showToast("Por favor, completa todos los campos.")
            return
        }

        // Validar que las contraseñas coincidan
        if (password != confirmPassword) {
            // Mostrar mensaje de error si las contraseñas no coinciden
            showToast("Las contraseñas no coinciden.")
            return
        }

        // Mostrar mensaje de éxito si todos los campos son válidos
        showToast("Usuario registrado correctamente.")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

