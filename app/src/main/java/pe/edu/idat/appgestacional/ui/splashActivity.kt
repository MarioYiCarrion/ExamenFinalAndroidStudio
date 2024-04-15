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


        ivsplash = findViewById(R.id.ivsplash)


        Glide.with(this)
            .asGif()
            .load(R.drawable.splash5) // Reemplaza "splash" por el nombre de tu archivo GIF en el directorio res/drawable
            .into(ivsplash)


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }, 3000)
    }
}