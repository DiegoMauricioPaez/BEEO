package com.beeo.app.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.beeo.app.R
import com.beeo.app.data.repository.AuthRepository
import com.beeo.app.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            delay(1800)

            val destination = if (authRepository.isLoggedIn) {
                // Usuario ya autenticado → ir directo al home
                MainActivity::class.java
            } else {
                // Sin sesión → pedir login
                LoginActivity::class.java
            }

            startActivity(Intent(this@SplashActivity, destination))
            finish()
        }
    }
}