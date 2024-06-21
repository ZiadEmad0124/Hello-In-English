package com.ziad_emad_dev.hello_in_english.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ziad_emad_dev.hello_in_english.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startApp()
    }

    private fun startApp() {
        Handler(Looper.getMainLooper())
            .postDelayed(
                {
                    val intent = Intent(this@SplashScreen, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                },
                1500
            )
    }
}