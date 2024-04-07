    package pe.edu.idat.appgestacional.fragment

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.fragment.app.Fragment
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.FirebaseUser
    import pe.edu.idat.appgestacional.R

    class NavHeaderFragment : Fragment() {

        private lateinit var mAuth: FirebaseAuth
        private lateinit var txtUsuario: TextView

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflar el diseño del encabezado del cajón de navegación
            val view = inflater.inflate(R.layout.nav_header_main, container, false)

            // Inicializar Firebase Authentication
            mAuth = FirebaseAuth.getInstance()

            // Encontrar la vista del TextView txtusuario
            txtUsuario = view.findViewById(R.id.txtusuario)

            // Obtener el usuario actual de Firebase Authentication
            val currentUser: FirebaseUser? = mAuth.currentUser

            // Verificar si el usuario está autenticado y tiene un nombre definido
            if (currentUser != null && currentUser.displayName != null) {
                // Obtener el nombre de usuario del usuario actual
                val userName: String = currentUser.displayName!!
                // Establecer el nombre de usuario en el TextView txtusuario
                txtUsuario.text = userName
            } else {
                // Si el usuario no está autenticado o no tiene un nombre definido, mostrar un mensaje predeterminado
                txtUsuario.text = "Usuario sin nombre"
            }

            return view
        }
    }
