package com.example.tareasqlite

import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Paso 6: usar la base de datos en la Activity.
class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: UsuarioAdapter
    private lateinit var etNombre: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button

    // Usuario que se está editando (null = modo "insertar")
    private var usuarioEditando: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        etNombre = findViewById(R.id.etNombre)
        etEmail = findViewById(R.id.etEmail)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)

        adapter = UsuarioAdapter(
            alSeleccionar = { empezarEdicion(it) },
            alEliminar = { eliminar(it) }
        )
        findViewById<RecyclerView>(R.id.rvUsuarios).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        btnGuardar.setOnClickListener { guardar() }
        btnCancelar.setOnClickListener { terminarEdicion() }

        cargarUsuarios()
    }

    private fun guardar() {
        val nombre = etNombre.text.toString().trim()
        val email = etEmail.text.toString().trim()
        if (nombre.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, R.string.campos_vacios, Toast.LENGTH_SHORT).show()
            return
        }

        // Paso 10: manejar los errores con try-catch
        try {
            val editando = usuarioEditando
            if (editando == null) {
                val usuario = Usuario(nombre = nombre, email = email)
                dbHelper.insertarUsuario(usuario)
                // Paso 9: verificar en Logcat
                Log.d("DB", "Usuario insertado: ${usuario.nombre}")
                Toast.makeText(this, R.string.usuario_guardado, Toast.LENGTH_SHORT).show()
            } else {
                dbHelper.actualizarUsuario(editando.copy(nombre = nombre, email = email))
                Log.d("DB", "Usuario actualizado: $nombre")
                Toast.makeText(this, R.string.usuario_actualizado, Toast.LENGTH_SHORT).show()
            }
            terminarEdicion()
            cargarUsuarios()
        } catch (e: SQLiteException) {
            Log.e("DB", "Error al guardar", e)
            Toast.makeText(this, R.string.error_bd, Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminar(usuario: Usuario) {
        try {
            dbHelper.eliminarUsuario(usuario.id)
            Log.d("DB", "Usuario eliminado: ${usuario.nombre}")
            Toast.makeText(this, R.string.usuario_eliminado, Toast.LENGTH_SHORT).show()
            if (usuarioEditando?.id == usuario.id) terminarEdicion()
            cargarUsuarios()
        } catch (e: SQLiteException) {
            Log.e("DB", "Error al eliminar", e)
            Toast.makeText(this, R.string.error_bd, Toast.LENGTH_SHORT).show()
        }
    }

    private fun empezarEdicion(usuario: Usuario) {
        usuarioEditando = usuario
        etNombre.setText(usuario.nombre)
        etEmail.setText(usuario.email)
        btnGuardar.setText(R.string.actualizar)
        btnCancelar.visibility = Button.VISIBLE
    }

    private fun terminarEdicion() {
        usuarioEditando = null
        etNombre.text.clear()
        etEmail.text.clear()
        btnGuardar.setText(R.string.guardar)
        btnCancelar.visibility = Button.GONE
    }

    private fun cargarUsuarios() {
        try {
            adapter.actualizarLista(dbHelper.obtenerUsuarios())
        } catch (e: SQLiteException) {
            Log.e("DB", "Error al leer", e)
        }
    }

    // Paso 10: cerrar la base de datos cuando no se necesite
    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
