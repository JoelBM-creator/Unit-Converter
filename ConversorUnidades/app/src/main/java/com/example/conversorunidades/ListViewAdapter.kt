package com.example.conversorunidades

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ListViewAdapter(private var context: Context, items: ArrayList<ModelMedida>): BaseAdapter() {
    // Creamos una variable del tipo ArrayList a partir de los objetos del tipo ModelMedida.
    private var items: ArrayList<ModelMedida>? = null

    init {
        this.items = items
    }

    // Cerramos la clase ViewHolder
    private class ViewHolder(vista: View) {
        var nombre: TextView? = null
        var imagen: ImageView? = null

        init {
            nombre = vista.findViewById(R.id.tituloItem)
            imagen = vista.findViewById(R.id.iconoItem)
        }
    }

    // Metodos del BaseAdapter
    override fun getCount(): Int {
        return items?.count()!!
    }

    override fun getItem(p0: Int): Any {
        return items?.get(p0)!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, View: View?, parent: ViewGroup?): View {
        var view = View
        val holder: ViewHolder?

        // Si la vista, esta vacía en este caso la creamos.
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fila, null)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as? ViewHolder
        }
        // Rellenamos la vista con los items según la posición.
        val item = items?.get(position)
        holder?.nombre?.text = item?.nombre
        holder?.imagen?.setImageResource(item?.image!!)

        return view!!
    }
}