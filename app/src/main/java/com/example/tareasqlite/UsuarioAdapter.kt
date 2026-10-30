package com.example.tareasqlite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adaptador del RecyclerView que muestra la lista de usuarios.
class UsuarioAdapter(
    private val alSeleccionar: (Usuario) -> Unit,
    private val alEliminar: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    private var usuarios: List<Usuario> = emptyList()

    fun actualizarLista(nuevos: List<Usuario>) {
        usuarios = nuevos
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUsuario: TextView = view.findViewById(R.id.tvUsuario)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.tvUsuario.text = "${position + 1}. ${usuario.nombre} - ${usuario.email}"
        holder.itemView.setOnClickListener { alSeleccionar(usuario) }
        holder.btnEliminar.setOnClickListener { alEliminar(usuario) }
    }

    override fun getItemCount() = usuarios.size
}
