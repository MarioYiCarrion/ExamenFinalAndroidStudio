package pe.edu.idat.appgestacional.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pe.edu.idat.appgestacional.R

class PrincipalFragment : Fragment() {

    private lateinit var tvnombreBienvenido: TextView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_principal, container, false)

        tvnombreBienvenido = view.findViewById(R.id.tvnombreBienvenido)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("User")

        mostrarNombreUsuario()

        return view
    }

    private fun mostrarNombreUsuario() {
        val currentUser: FirebaseUser? = auth.currentUser
        currentUser?.let { user ->
            val userId: String = user.uid
            val userRef: DatabaseReference = databaseReference.child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val nombre: String? = dataSnapshot.child("Nombre").getValue(String::class.java)
                        tvnombreBienvenido.text = nombre
                    } else {
                        // Manejar el caso en el que el usuario no tenga un nombre registrado
                        tvnombreBienvenido.text = "¡Bienvenido!"
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Manejar errores de lectura de Firebase Database
                }
            })
        }
    }
}
