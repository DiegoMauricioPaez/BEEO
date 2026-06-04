package com.beeo.app.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de INTEGRACIÓN con Firebase Emulator.
 * Requieren tener corriendo el Firebase Local Emulator Suite.
 *
 * Setup del emulador (ejecutar antes de correr estas pruebas):
 *   npm install -g firebase-tools
 *   firebase emulators:start --only auth,firestore
 *
 * Ruta: app/src/androidTest/java/com/beeo/app/data/repository/AuthRepositoryIntegrationTest.kt
 *
 * Para correr SOLO estas pruebas:
 *   ./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.beeo.app.data.repository.AuthRepositoryIntegrationTest
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AuthRepositoryIntegrationTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var authRepository: AuthRepository

    companion object {
        // Dirección del emulador — cambiar si tu máquina tiene IP diferente
        private const val EMULATOR_HOST = "10.0.2.2" // IP de la máquina host desde el emulador Android
        private const val AUTH_EMULATOR_PORT = 9099
        private const val FIRESTORE_EMULATOR_PORT = 8080
        private const val TEST_EMAIL = "testuser@beeo.app"
        private const val TEST_PASSWORD = "Test@12345"
    }

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Inicializar Firebase si no está inicializado
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context)
        }

        // Conectar al emulador local (NUNCA al proyecto de producción)
        firebaseAuth = FirebaseAuth.getInstance().apply {
            useEmulator(EMULATOR_HOST, AUTH_EMULATOR_PORT)
        }

        firestore = FirebaseFirestore.getInstance().apply {
            useEmulator(EMULATOR_HOST, FIRESTORE_EMULATOR_PORT)
            firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false) // Sin caché local en tests
                .build()
        }

        authRepository = AuthRepository(firebaseAuth, firestore)
    }

    @After
    fun tearDown() {
        // Limpiar sesión después de cada test
        firebaseAuth.signOut()
    }

    /**
     * ESCENARIO: Verificar estado inicial sin sesión.
     * ESPERADO: currentUser null, isLoggedIn false.
     */
    @Test
    fun test01_estadoInicialSinSesion() {
        firebaseAuth.signOut()
        assertNull("No debe haber usuario autenticado al inicio", authRepository.currentUser)
        assertTrue("isLoggedIn debe ser false", !authRepository.isLoggedIn)
    }

    /**
     * ESCENARIO: Crear usuario con email/password en el emulador y verificar que
     * se puede leer desde el repositorio. (Google Sign-In real no se puede simular
     * en tests instrumentados sin interacción del usuario.)
     */
    @Test
    fun test02_crearUsuarioEnEmuladorYVerificarSesion() = runBlocking {
        // Crear usuario en el emulador de Auth
        val authResult = firebaseAuth
            .createUserWithEmailAndPassword(TEST_EMAIL, TEST_PASSWORD)
            .await()

        val user = authResult.user
        assertNotNull("El usuario creado no debe ser null", user)
        assertTrue("isLoggedIn debe ser true después del registro", authRepository.isLoggedIn)
        assertTrue("currentUser debe ser el usuario recién creado", authRepository.currentUser?.email == TEST_EMAIL)
    }

    /**
     * ESCENARIO: Guardar datos de usuario en Firestore y verificar que se pueden leer.
     */
    @Test
    fun test03_guardarYLeerUsuarioEnFirestore() = runBlocking {
        // Crear usuario
        val authResult = firebaseAuth
            .createUserWithEmailAndPassword(TEST_EMAIL, TEST_PASSWORD)
            .await()
        val uid = authResult.user!!.uid

        // Escribir documento manualmente (simula lo que hace saveUserToFirestore)
        val userData = mapOf(
            "uid" to uid,
            "email" to TEST_EMAIL,
            "displayName" to "Test User",
            "createdAt" to System.currentTimeMillis()
        )
        firestore.collection("users").document(uid).set(userData).await()

        // Leer y verificar
        val snapshot = firestore.collection("users").document(uid).get().await()
        assertTrue("El documento debe existir en Firestore", snapshot.exists())
        assertEquals("El email guardado debe coincidir", TEST_EMAIL, snapshot.getString("email"))
        assertNotNull("createdAt debe estar presente", snapshot.getLong("createdAt"))
    }

    /**
     * ESCENARIO: Actualizar lastLoginAt sin perder createdAt (merge behavior).
     */
    @Test
    fun test04_mergeNoSobreescribeCreatedAt() = runBlocking {
        val authResult = firebaseAuth
            .createUserWithEmailAndPassword(TEST_EMAIL, TEST_PASSWORD)
            .await()
        val uid = authResult.user!!.uid

        val originalCreatedAt = 1000000L

        // Primer guardado con createdAt
        firestore.collection("users").document(uid).set(
            mapOf("uid" to uid, "email" to TEST_EMAIL, "createdAt" to originalCreatedAt)
        ).await()

        // Segundo guardado (login posterior) — simula merge, sin createdAt
        firestore.collection("users").document(uid)
            .set(mapOf("lastLoginAt" to System.currentTimeMillis()),
                com.google.firebase.firestore.SetOptions.merge())
            .await()

        // Verificar que createdAt NO fue sobreescrito
        val snapshot = firestore.collection("users").document(uid).get().await()
        assertEquals(
            "createdAt no debe ser sobreescrito por merge",
            originalCreatedAt,
            snapshot.getLong("createdAt")
        )
        assertNotNull("lastLoginAt debe existir después del merge", snapshot.getLong("lastLoginAt"))
    }

    /**
     * ESCENARIO: signOut limpia la sesión.
     */
    @Test
    fun test05_signOutLimpiaLaSesion() = runBlocking {
        // Crear sesión
        firebaseAuth.createUserWithEmailAndPassword(TEST_EMAIL, TEST_PASSWORD).await()
        assertTrue("Debe haber sesión activa", authRepository.isLoggedIn)

        // Cerrar sesión
        authRepository.signOut()

        assertNull("currentUser debe ser null después de signOut", authRepository.currentUser)
        assertTrue("isLoggedIn debe ser false después de signOut", !authRepository.isLoggedIn)
    }

    private fun assertEquals(message: String, expected: Any?, actual: Any?) {
        org.junit.Assert.assertEquals(message, expected, actual)
    }
}
