package br.com.sodepebrasil.sodepeapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.splash_main.*
import android.view.WindowManager
import android.os.Build
import android.support.v4.app.ActivityCompat


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_main)

        // Ativa Immersive mode.
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        // Aplica a animação na Activity
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        iv_logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))

        // Seta a animação com o tempo de tela, e inicia a próxima activity
        Handler().postDelayed({
            iv_logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
            Handler().postDelayed({
                iv_logo.visibility = View.GONE
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            },500)
        },2500)
    }
}
