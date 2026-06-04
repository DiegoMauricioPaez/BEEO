package com.beeo.app.data.repository

import com.beeo.app.domain.model.UserFirestoreModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

sealed class AuthResult {
    data class Success(val user: FirebaseUser) : AuthResult()
    data class Error(val message: String) : AuthResult()
    object Cancelled : AuthResult()
}

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    /** Usuario actualmente autenticado, o null si no hay sesión */
    val currentUser: FirebaseUser? get() = firebaseAuth.currentUser

    val isLoggedIn: Boolean get() = firebaseAuth.currentUser != null

    /**
     * Completa el login con Google usando el idToken obtenido del Intent de Google.
     * Retorna AuthResult.Success, AuthResult.Error o AuthResult.Cancelled.
     */
    suspend fun signInWithGoogle(idToken: String?): AuthResult {
        if (idToken == null) return AuthResult.Cancelled

        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user ?: return AuthResult.Error("No se pudo obtener el usuario.")

            // Guardar / actualizar datos en Firestore
            saveUserToFirestore(user, isNewUser = authResult.additionalUserInfo?.isNewUser == true)

            AuthResult.Success(user)
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Error desconocido al iniciar sesión.")
        }
    }

    /**
     * Guarda la información inicial del usuario en Firestore.
     * Usa SetOptions.merge() para no sobreescribir datos si el usuario ya existía.
     */
    private suspend fun saveUserToFirestore(user: FirebaseUser, isNewUser: Boolean) {
        val userData = mutableMapOf<String, Any>(
            "uid" to user.uid,
            "email" to (user.email ?: ""),
            "displayName" to (user.displayName ?: ""),
            "photoUrl" to (user.photoUrl?.toString() ?: ""),
            "lastLoginAt" to System.currentTimeMillis()
        )

        // Solo agrega createdAt si es usuario nuevo
        if (isNewUser) {
            userData["createdAt"] = System.currentTimeMillis()
        }

        // merge() no destruye campos existentes si el documento ya existe
        firestore.collection("users")
            .document(user.uid)
            .set(userData, SetOptions.merge())
            .await()
    }

    /** Cierra la sesión de Firebase */
    fun signOut() {
        firebaseAuth.signOut()
    }
}