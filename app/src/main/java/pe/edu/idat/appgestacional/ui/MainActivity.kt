package pe.edu.idat.appgestacional.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import pe.edu.idat.appgestacional.R
import pe.edu.idat.appgestacional.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var txtusuario: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño de la actividad principal
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la barra de herramientas
        setSupportActionBar(binding.appBarMain.toolbar)

        // Configurar el botón flotante
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // Inicializar Firebase Authentication y DatabaseReference
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("User")

        // Encontrar el TextView txtusuario en el encabezado de navegación
        val headerView = binding.navView.getHeaderView(0)
        txtusuario = headerView.findViewById(R.id.txtusuario)

        // Obtener el usuario actualmente autenticado y mostrar su correo electrónico en txtusuario
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val email = currentUser.email
            txtusuario.text = email
        }

        // Configurar la navegación con el controlador de navegación y el DrawerLayout
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.principalFragment, R.id.seguimientoFragment, R.id.citasFragment,
                R.id.medicionyPruebasFragment, R.id.diarioFragment, R.id.consejosFragment,
                R.id.recordatoriosyAlertasFragment, R.id.historialCitasFragment
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflar el menú de opciones
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Manejar los clics en los elementos del menú
        when (item.itemId) {
            R.id.Logout -> {
                // Cerrar sesión y redirigir al usuario a la actividad de inicio de sesión
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() // Cierra la actividad actual
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Navegar hacia arriba en la jerarquía de la navegación
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
