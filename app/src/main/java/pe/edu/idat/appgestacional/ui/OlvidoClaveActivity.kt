package pe.edu.idat.appgestacional.ui


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import pe.edu.idat.appgestacional.R

class OlvidoClaveActivity : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_olvido_clave)
        txtEmail=findViewById(R.id.txtCorreo)
        auth= FirebaseAuth.getInstance()
    }

    fun enviarcambio(view:View){
        val correo=txtEmail.text.toString()
        if(!TextUtils.isEmpty(correo)){
            auth.sendPasswordResetEmail(correo)
                .addOnCompleteListener(this){
                    task ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Se envio Correo para Cambio de Clave", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    }else{
                        Toast.makeText(this,"Error al Enviar el Correo", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}