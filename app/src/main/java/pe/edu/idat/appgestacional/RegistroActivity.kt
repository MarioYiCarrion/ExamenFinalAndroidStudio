package pe.edu.idat.appgestacional

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.view.View

class RegistroActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextDNI: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextCelular: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_registro)

        // Inicialización de vistas
        editTextName = findViewById(R.id.editTextName)
        editTextDNI = findViewById(R.id.editTextDNI)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextCelular = findViewById(R.id.editTextCelular)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        progressBar = findViewById(R.id.progressBar)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        dbReference = database.reference.child("User")
    }

    fun register(view: android.view.View) {
        CrearNuevaCuenta()

    }

    private fun CrearNuevaCuenta(){
        val nombre:String=editTextName.text.toString()
        val DNI:String=editTextDNI.text.toString()
        val direccion:String=editTextDireccion.text.toString()
        val email:String=editTextEmail.text.toString()
        val celular:String=editTextCelular.text.toString()
        val clave:String=editTextPassword.text.toString()
        val confirmaClave:String=editTextConfirmPassword.text.toString()

        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(DNI) && !TextUtils.isEmpty(direccion) &&
            !TextUtils.isEmpty(email) && !TextUtils.isEmpty(celular) && !TextUtils.isEmpty(clave) &&
            !TextUtils.isEmpty(confirmaClave)){
            progressBar.visibility=android.view.View.VISIBLE

            auth.createUserWithEmailAndPassword(email, clave)
                .addOnCompleteListener(this){
                    task ->

                    if(task.isComplete){
                        val user:FirebaseUser?=auth.currentUser
                        verifyEmail(user)

                        val userBD=dbReference.child(user?.uid.toString())

                        userBD.child("Nombre").setValue(nombre)
                        userBD.child("DNI").setValue(DNI)
                        userBD.child("direccion").setValue(direccion)
                        userBD.child("email").setValue(email)
                        userBD.child("celular").setValue(celular)
                        userBD.child("clave").setValue(clave)
                        action()


                    }
                }

        }

    }

    private fun action(){
        startActivity(Intent(this,LoginActivity::class.java))
    }

    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                task->

                if(task.isComplete){
                    Toast.makeText(this,"Correo de Verificacion Enviado", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Error al Enviar Correo de Verificacion", Toast.LENGTH_LONG).show()
                }
            }
    }

}
