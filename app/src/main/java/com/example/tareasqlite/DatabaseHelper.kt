package com.example.tareasqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Paso 2: clase de ayuda que crea y gestiona la base de datos "miBD.db".
// Paso 5: operaciones CRUD (insertar, leer, actualizar, eliminar).
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "miBD.db"
        const val DATABASE_VERSION = 1
        const val TABLA_USUARIOS = "usuarios"
    }

    // Se ejecuta la primera vez que se crea la BD
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLA_USUARIOS (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, email TEXT)"
        )
    }

    // Lógica de actualización: aquí simplemente se recrea la tabla
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_USUARIOS")
        onCreate(db)
    }

    fun insertarUsuario(usuario: Usuario): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", usuario.nombre)
            put("email", usuario.email)
        }
        return db.insert(TABLA_USUARIOS, null, valores)
    }

    fun obtenerUsuarios(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val db = readableDatabase
        db.query(TABLA_USUARIOS, arrayOf("id", "nombre", "email"), null, null, null, null, "id ASC")
            .use { cursor ->
                while (cursor.moveToNext()) {
                    usuarios.add(
                        Usuario(
                            id = cursor.getInt(0),
                            nombre = cursor.getString(1),
                            email = cursor.getString(2)
                        )
                    )
                }
            }
        return usuarios
    }

    fun actualizarUsuario(usuario: Usuario): Int {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", usuario.nombre)
            put("email", usuario.email)
        }
        return db.update(TABLA_USUARIOS, valores, "id = ?", arrayOf(usuario.id.toString()))
    }

    fun eliminarUsuario(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLA_USUARIOS, "id = ?", arrayOf(id.toString()))
    }
}
