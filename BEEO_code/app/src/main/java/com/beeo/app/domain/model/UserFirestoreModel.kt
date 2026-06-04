package com.beeo.app.domain.model

/**
 * Modelo que se guarda en Firestore bajo la colección "users/{uid}"
 */
data class UserFirestoreModel(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = System.currentTimeMillis()
)