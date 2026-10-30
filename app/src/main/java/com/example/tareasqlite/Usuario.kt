package com.example.tareasqlite

// Paso 4: modelo (clase de entidad) que representa la tabla "usuarios".
data class Usuario(
    val id: Int = 0,
    val nombre: String,
    val email: String
)
