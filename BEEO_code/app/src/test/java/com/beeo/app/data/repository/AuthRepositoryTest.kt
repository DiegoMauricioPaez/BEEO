package com.beeo.app.data.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AdditionalUserInfo
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import com.beeo.app.data.repository.AuthResult as BEEOAuthResult

/**
 * Pruebas unitarias para AuthRepository.
 * Cubre: login exitoso (usuario nuevo), login exitoso (usuario existente),
 * token nulo, error de red, error de Firestore, estado de sesión y signOut.
 *
 * Ruta: app/src/test/java/com/beeo/app/data/repository/AuthRepositoryTest.kt
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    // ─── Mocks de Firebase ───────────────────────────────────────────────────

    @Mock private lateinit var mockFirebaseAuth: FirebaseAuth
    @Mock private lateinit var mockFirestore: FirebaseFirestore
    @Mock private lateinit var mockFirebaseUser: FirebaseUser
    @Mock private lateinit var mockAuthResult: AuthResult
    @Mock private lateinit var mockAdditionalUserInfo: AdditionalUserInfo
    @Mock private lateinit var mockCollectionRef: CollectionReference
    @Mock private lateinit var mockDocumentRef: DocumentReference

    private lateinit var authRepository: AuthRepository

    // ─── Setup ───────────────────────────────────────────────────────────────

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authRepository = AuthRepository(mockFirebaseAuth, mockFirestore)

        // Datos por defecto del usuario mock
        `when`(mockFirebaseUser.uid).thenReturn("test_uid_123")
        `when`(mockFirebaseUser.email).thenReturn("test@gmail.com")
        `when`(mockFirebaseUser.displayName).thenReturn("Test User")
        `when`(mockFirebaseUser.photoUrl).thenReturn(null)

        // Encadenar Firestore: firestore.collection("users").document(uid).set(...)
        `when`(mockFirestore.collection("users")).thenReturn(mockCollectionRef)
        `when`(mockCollectionRef.document(anyString())).thenReturn(mockDocumentRef)
        `when`(mockDocumentRef.set(any(), any(SetOptions::class.java)))
            .thenReturn(Tasks.forResult(null))
    }

    // ─── Tests de signInWithGoogle ────────────────────────────────────────────

    /**
     * ESCENARIO: idToken es null (usuario cerró el popup de Google sin seleccionar cuenta).
     * ESPERADO: retorna AuthResult.Cancelled, NO llama a Firebase Auth.
     */
    @Test
    fun `signInWithGoogle con idToken null retorna Cancelled`() = runTest {
        val result = authRepository.signInWithGoogle(null)

        assertTrue(
            "Debe retornar Cancelled cuando idToken es null",
            result is BEEOAuthResult.Cancelled
        )
        verify(mockFirebaseAuth, never()).signInWithCredential(any())
    }

    /**
     * ESCENARIO: Login exitoso con usuario NUEVO (primer inicio de sesión).
     * ESPERADO: retorna Success, guarda en Firestore con createdAt incluido.
     */
    @Test
    fun `signInWithGoogle exitoso con usuario nuevo retorna Success y guarda en Firestore`() = runTest {
        `when`(mockAuthResult.user).thenReturn(mockFirebaseUser)
        `when`(mockAuthResult.additionalUserInfo).thenReturn(mockAdditionalUserInfo)
        `when`(mockAdditionalUserInfo.isNewUser).thenReturn(true)
        `when`(mockFirebaseAuth.signInWithCredential(any()))
            .thenReturn(Tasks.forResult(mockAuthResult))

        val result = authRepository.signInWithGoogle("valid_id_token")

        assertTrue(
            "Debe retornar Success para usuario nuevo",
            result is BEEOAuthResult.Success
        )
        assertEquals(
            "El usuario retornado debe ser el mock",
            mockFirebaseUser,
            (result as BEEOAuthResult.Success).user
        )
        // Verificar que se intentó escribir en Firestore
        verify(mockFirestore).collection("users")
        verify(mockCollectionRef).document("test_uid_123")
    }

    /**
     * ESCENARIO: Login exitoso con usuario EXISTENTE (ya tiene cuenta).
     * ESPERADO: retorna Success, guarda en Firestore SIN incluir createdAt.
     */
    @Test
    fun `signInWithGoogle exitoso con usuario existente retorna Success`() = runTest {
        `when`(mockAuthResult.user).thenReturn(mockFirebaseUser)
        `when`(mockAuthResult.additionalUserInfo).thenReturn(mockAdditionalUserInfo)
        `when`(mockAdditionalUserInfo.isNewUser).thenReturn(false)
        `when`(mockFirebaseAuth.signInWithCredential(any()))
            .thenReturn(Tasks.forResult(mockAuthResult))

        val result = authRepository.signInWithGoogle("valid_id_token")

        assertTrue(
            "Debe retornar Success para usuario existente",
            result is BEEOAuthResult.Success
        )
    }

    /**
     * ESCENARIO: Firebase Auth devuelve un AuthResult sin usuario (user == null).
     * ESPERADO: retorna Error con mensaje descriptivo, NO intenta escribir en Firestore.
     */
    @Test
    fun `signInWithGoogle cuando user es null retorna Error`() = runTest {
        `when`(mockAuthResult.user).thenReturn(null)
        `when`(mockFirebaseAuth.signInWithCredential(any()))
            .thenReturn(Tasks.forResult(mockAuthResult))

        val result = authRepository.signInWithGoogle("valid_id_token")

        assertTrue(
            "Debe retornar Error cuando user es null",
            result is BEEOAuthResult.Error
        )
        verify(mockDocumentRef, never()).set(any(), any(SetOptions::class.java))
    }

    /**
     * ESCENARIO: Fallo de red — Firebase Auth lanza una excepción.
     * ESPERADO: retorna Error con el mensaje de la excepción.
     */
    @Test
    fun `signInWithGoogle con error de red retorna Error`() = runTest {
        val networkException = Exception("Network error: Unable to resolve host")
        `when`(mockFirebaseAuth.signInWithCredential(any()))
            .thenReturn(Tasks.forException(networkException))

        val result = authRepository.signInWithGoogle("valid_id_token")

        assertTrue(
            "Debe retornar Error en fallo de red",
            result is BEEOAuthResult.Error
        )
        assertTrue(
            "El mensaje debe contener información del error",
            (result as BEEOAuthResult.Error).message.isNotEmpty()
        )
    }

    /**
     * ESCENARIO: Credenciales inválidas — token expirado o manipulado.
     * ESPERADO: retorna Error.
     */
    @Test
    fun `signInWithGoogle con credenciales invalidas retorna Error`() = runTest {
        val authException = FirebaseAuthInvalidCredentialsException(
            "ERROR_INVALID_CREDENTIAL",
            "The supplied auth credential is malformed or has expired."
        )
        `when`(mockFirebaseAuth.signInWithCredential(any()))
            .thenReturn(Tasks.forException(authException))

        val result = authRepository.signInWithGoogle("expired_token")

        assertTrue(
            "Debe retornar Error para credenciales inválidas",
            result is BEEOAuthResult.Error
        )
    }

    /**
     * ESCENARIO: Auth exitoso pero Firestore falla al guardar.
     * ESPERADO: retorna Error (la operación completa falla si no se puede persistir).
     */
    @Test
    fun `signInWithGoogle cuando Firestore falla retorna Error`() = runTest {
        val firestoreException = Exception("Firestore: UNAVAILABLE")
        `when`(mockAuthResult.user).thenReturn(mockFirebaseUser)
        `when`(mockAuthResult.additionalUserInfo).thenReturn(mockAdditionalUserInfo)
        `when`(mockAdditionalUserInfo.isNewUser).thenReturn(true)
        `when`(mockFirebaseAuth.signInWithCredential(any()))
            .thenReturn(Tasks.forResult(mockAuthResult))
        `when`(mockDocumentRef.set(any(), any(SetOptions::class.java)))
            .thenReturn(Tasks.forException(firestoreException))

        val result = authRepository.signInWithGoogle("valid_id_token")

        assertTrue(
            "Debe retornar Error cuando Firestore no está disponible",
            result is BEEOAuthResult.Error
        )
    }

    // ─── Tests de estado de sesión ────────────────────────────────────────────

    /**
     * ESCENARIO: Hay un usuario autenticado en Firebase.
     * ESPERADO: currentUser retorna el usuario, isLoggedIn es true.
     */
    @Test
    fun `currentUser retorna usuario cuando hay sesion activa`() {
        `when`(mockFirebaseAuth.currentUser).thenReturn(mockFirebaseUser)

        assertEquals(mockFirebaseUser, authRepository.currentUser)
        assertTrue("isLoggedIn debe ser true con sesión activa", authRepository.isLoggedIn)
    }

    /**
     * ESCENARIO: No hay sesión activa.
     * ESPERADO: currentUser es null, isLoggedIn es false.
     */
    @Test
    fun `currentUser retorna null cuando no hay sesion`() {
        `when`(mockFirebaseAuth.currentUser).thenReturn(null)

        assertNull("currentUser debe ser null sin sesión", authRepository.currentUser)
        assertTrue("isLoggedIn debe ser false sin sesión", !authRepository.isLoggedIn)
    }

    // ─── Tests de signOut ─────────────────────────────────────────────────────

    /**
     * ESCENARIO: Usuario cierra sesión.
     * ESPERADO: se llama a firebaseAuth.signOut() exactamente una vez.
     */
    @Test
    fun `signOut llama a firebaseAuth signOut`() {
        authRepository.signOut()
        verify(mockFirebaseAuth).signOut()
    }
}
