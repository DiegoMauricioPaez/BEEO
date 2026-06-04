package com.beeo.app.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.beeo.app.R
import com.beeo.app.data.repository.AuthRepository
import com.beeo.app.data.repository.AuthResult
import com.beeo.app.presentation.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

// Layout mínimo requerido: activity_login.xml con:
//   - Un Button con id="btn_google_login"
//   - Un ProgressBar con id="progress_bar" (visibility="gone" por defecto)

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject lateinit var authRepository: AuthRepository

    private lateinit var googleSignInClient: GoogleSignInClient

    // Lanzador del Intent de Google Sign-In
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        handleGoogleSignInResult(result.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupGoogleSignIn()
        setupClickListeners()
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // default_web_client_id se genera automáticamente del google-services.json
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupClickListeners() {
        findViewById<View>(R.id.btn_google_login).setOnClickListener {
            startGoogleSignIn()
        }
    }

    private fun startGoogleSignIn() {
        setLoading(true)
        // Forzar selección de cuenta cada vez (útil en desarrollo)
        googleSignInClient.signOut().addOnCompleteListener {
            googleSignInLauncher.launch(googleSignInClient.signInIntent)
        }
    }

    private fun handleGoogleSignInResult(data: Intent?) {
        if (data == null) {
            // El usuario canceló la pantalla de Google
            setLoading(false)
            return
        }

        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            lifecycleScope.launch {
                val result = authRepository.signInWithGoogle(idToken)
                setLoading(false)

                when (result) {
                    is AuthResult.Success -> {
                        navigateToMain()
                    }
                    is AuthResult.Error -> {
                        showError(result.message)
                    }
                    is AuthResult.Cancelled -> {
                        // No hacer nada, el usuario canceló
                    }
                }
            }
        } catch (e: ApiException) {
            setLoading(false)
            when (e.statusCode) {
                12501 -> { /* Cancelado por el usuario, no mostrar error */ }
                7 -> showError("Sin conexión a internet. Verifica tu red.")
                else -> showError("Error al conectar con Google (código: ${e.statusCode})")
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setLoading(loading: Boolean) {
        findViewById<View>(R.id.btn_google_login).isEnabled = !loading
        val progressBar = findViewById<View?>(R.id.progress_bar)
        progressBar?.visibility = if (loading) View.VISIBLE else View.GONE
    }
}