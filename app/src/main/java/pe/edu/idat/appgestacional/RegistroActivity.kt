package pe.edu.idat.appgestacional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

class RegistroActivity : AppCompatActivity() {

    private lateinit var buttonRegistrar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_registro)

        //para implemeentar que no se permita dejar espacios en usuario
        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)

        editTextUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementación
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No se necesita implementación
            }

            override fun afterTextChanged(s: Editable?) {
                // Verificar y eliminar espacios en blanco al principio o al final
                if (s != null && s.isNotEmpty() && (s.startsWith(" ") || s.endsWith(" "))) {
                    editTextUsername.setText(s.trim()) // Eliminar espacios en blanco
                    editTextUsername.setSelection(editTextUsername.text.length) // Colocar el cursor al final
                }
            }
        }) //aca acaba la implementacion de espacios en blanco

        buttonRegistrar = findViewById(R.id.buttonRegister)

        buttonRegistrar.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}
