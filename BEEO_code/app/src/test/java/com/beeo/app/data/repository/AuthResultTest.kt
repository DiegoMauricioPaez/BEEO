package com.beeo.app.data.repository

import com.google.firebase.auth.FirebaseUser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Pruebas unitarias para el sealed class AuthResult.
 * Verifica que cada estado lleve el dato correcto y que el when sea exhaustivo.
 *
 * Ruta: app/src/test/java/com/beeo/app/data/repository/AuthResultTest.kt
 */
class AuthResultTest {

    @Test
    fun `AuthResult Success contiene el FirebaseUser correcto`() {
        val mockUser = mock(FirebaseUser::class.java)
        val result = AuthResult.Success(mockUser)

        assertTrue(result is AuthResult.Success)
        assertEquals(mockUser, result.user)
    }

    @Test
    fun `AuthResult Error contiene el mensaje correcto`() {
        val errorMessage = "Error de autenticación"
        val result = AuthResult.Error(errorMessage)

        assertTrue(result is AuthResult.Error)
        assertEquals(errorMessage, result.message)
    }

    @Test
    fun `AuthResult Error con mensaje vacio es valido`() {
        val result = AuthResult.Error("")
        assertTrue(result is AuthResult.Error)
        assertTrue(result.message.isEmpty())
    }

    @Test
    fun `AuthResult Cancelled es un objeto singleton`() {
        val result1 = AuthResult.Cancelled
        val result2 = AuthResult.Cancelled
        // Los objetos Kotlin son singletons, deben ser la misma referencia
        assertTrue(result1 === result2)
    }

    @Test
    fun `when sobre AuthResult cubre todos los casos sin else`() {
        val results = listOf(
            AuthResult.Success(mock(FirebaseUser::class.java)),
            AuthResult.Error("test error"),
            AuthResult.Cancelled
        )

        // Si AuthResult tuviera un nuevo estado y no se actualizara el when,
        // este test fallaría al compilar (exhaustive when en Kotlin)
        results.forEach { result ->
            val label = when (result) {
                is AuthResult.Success -> "success"
                is AuthResult.Error -> "error"
                is AuthResult.Cancelled -> "cancelled"
            }
            assertTrue(label.isNotEmpty())
        }
    }
}
