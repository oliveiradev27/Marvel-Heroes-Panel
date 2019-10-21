package br.espartano.marvelheroescatalog.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.espartano.marvelheroescatalog.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({ showListActivity() }, 2000)
    }

    private fun showListActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
