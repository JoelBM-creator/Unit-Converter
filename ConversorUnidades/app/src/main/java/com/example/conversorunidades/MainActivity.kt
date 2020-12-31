package com.example.conversorunidades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Añadimos las objetos del modelo de Medida.
        val medidas = ArrayList<ModelMedida>()

        medidas.add(ModelMedida(getString(R.string.peso), R.drawable.peso))
        medidas.add(ModelMedida(getString(R.string.liquido), R.drawable.liquido))
        medidas.add(ModelMedida(getString(R.string.distancia), R.drawable.distancia))
        medidas.add(ModelMedida(getString(R.string.temperatura), R.drawable.temperatura))
        medidas.add(ModelMedida(getString(R.string.capacidad), R.drawable.capacidadhdd))

        // Creamos el ListView y le añadimos el adaptador.
        val lista:ListView = findViewById(R.id.listaItems)
        val adaptador = ListViewAdapter(this, medidas)
        lista.adapter = adaptador

        // Listener al hacer click en cualquier item de la Lista, según la posición
        lista.onItemClickListener = AdapterView.OnItemClickListener { _, _, posicion, _ ->
            if (posicion == 5) {
                finish()
            } else {
                // Nos muestra un toast según la posición del item Medida seleccionado.
                Toast.makeText(this, "Ha seleccionado: " + medidas[posicion].nombre, Toast.LENGTH_SHORT).show()

                //Enviamos el intent del item Medida seleccionado al Activity de Conversion.
                val intent = Intent(this, ConversionActivity::class.java)
                intent.putExtra("posicion", posicion)
                startActivity(intent)
            }
        }
    }
}