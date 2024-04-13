package pe.edu.idat.appgestacional.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import pe.edu.idat.appgestacional.R

class splashActivity : AppCompatActivity() {

    private lateinit var ivsplash: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        // Inicializar la vista ivsplash
        ivsplash = findViewById(R.id.ivsplash)

        // Cargar y mostrar el GIF utilizando Glide
        Glide.with(this)
            .asGif()

            .load(R.drawable.splash5) // Reemplaza "splash" por el nombre de tu archivo GIF en el directorio res/drawable

            .load(R.drawable.splash5) // Reemplaza "splash" por el nombre de tu archivo GIF en el directorio res/drawable

            .into(ivsplash)

        // Esperar 10 segundos antes de iniciar LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }, 3000)
    }
}
