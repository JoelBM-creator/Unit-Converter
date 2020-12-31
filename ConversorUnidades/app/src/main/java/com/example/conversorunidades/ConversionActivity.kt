package com.example.conversorunidades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_conversion.*
import kotlin.math.pow

class ConversionActivity : AppCompatActivity() {
    // Declaramos las variables, que obtendremos los datos tanto de las unidades como el valor insertado.
    private var insertado: Double = 0.00
    private var unidad1: Int = -1
    private var unidad2: Int = -1
    private var resultado: Double = 0.00

    // Botón (Atras) en el ToolBar para ir al MainActivity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)

        //Obtenemos la posición seleccionada por el usuario de los items de las unidades del Activity Main
        val intent: Intent = intent
        val posicion: Int = intent.getIntExtra("posicion", 0)

        //Dependiendo de la posición seleccionada, mostraremos un titulo distinto, imagen y cargaremos los spinners.
        when(posicion) {
            0 -> {
                setTitle(R.string.tituloPeso)
                inputCantidad.inputType = InputType.TYPE_CLASS_NUMBER
                iconUnidad.setImageResource(R.drawable.peso)
                val spinnerAdapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
                    this, R.array.unidadPeso, android.R.layout.simple_spinner_item
                )
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spUnidad1.adapter = spinnerAdapter
                spUnidad2.adapter = spinnerAdapter
            }
            1 -> {
                setTitle(R.string.tituloLiquido)
                inputCantidad.inputType = InputType.TYPE_CLASS_NUMBER
                iconUnidad.setImageResource(R.drawable.liquido)
                val spinnerAdapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
                    this, R.array.unidadLiquido, android.R.layout.simple_spinner_item
                )
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spUnidad1.adapter = spinnerAdapter
                spUnidad2.adapter = spinnerAdapter
            }
            2 -> {
                setTitle(R.string.tituloDistancia)
                inputCantidad.inputType = InputType.TYPE_CLASS_NUMBER
                iconUnidad.setImageResource(R.drawable.distancia)
                val spinnerAdapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
                    this, R.array.unidadDistancia, android.R.layout.simple_spinner_item
                )
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spUnidad1.adapter = spinnerAdapter
                spUnidad2.adapter = spinnerAdapter
            }
            3 -> {
                setTitle(R.string.tituloTemperatura)
                inputCantidad.inputType = InputType.TYPE_CLASS_NUMBER
                iconUnidad.setImageResource(R.drawable.temperatura)
                val spinnerAdapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
                    this, R.array.unidadTemp, android.R.layout.simple_spinner_item
                )
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spUnidad1.adapter = spinnerAdapter
                spUnidad2.adapter = spinnerAdapter
            }
            4 -> {
                setTitle(R.string.tituloCapacidad)
                inputCantidad.inputType = InputType.TYPE_CLASS_NUMBER
                iconUnidad.setImageResource(R.drawable.capacidadhdd)
                val spinnerAdapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
                    this, R.array.unidadCapacidad, android.R.layout.simple_spinner_item
                )
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spUnidad1.adapter = spinnerAdapter
                spUnidad2.adapter = spinnerAdapter
            }
        }

        /*
        Al pulsar el botón Convertir:
        Obtenemos los datos del valor añadido por el TextInput,
        en caso que sea distinto a 0 no nos saltara error, si es 0 nos salta error.
        Haremos lo mismo con los Spinners, en caso de que sea distinta a la mayor 1 opción que es la "Default",
        no nos salte error.

        En caso de que este correcto los spinner y el valor, llamaremos al método del calculo,
        dependiendo de la posición que hemos seleccionado anteriormente.
         */
        buttonConvert.setOnClickListener {
            insertado = obtenerValor()
            if(insertado != 0.00) {
                obtenerDatosSp1()
                if(unidad1 < 1) {
                    viewError.setText(R.string.errorSp1)
                } else {
                    viewError.text = ""
                    viewResultado.text = ""
                    obtenerDatosSp2()
                    if(unidad2 < 1){
                        viewError.setText(R.string.errorSp2)
                    } else {
                        viewError.text = ""
                        viewResultado.text = ""
                        when(posicion) {
                            0 -> {
                                calculoUnidades(insertado, unidad1, unidad2)
                            }
                            1 -> {
                                calculoUnidades(insertado, unidad1, unidad2)
                            }
                            2 -> {
                                calculoUnidades(insertado, unidad1, unidad2)
                            }
                            3 -> {
                                calcularTemparatura(insertado, unidad1, unidad2)
                            }
                            4 -> {
                                calcularCapacidad(insertado, unidad1, unidad2)
                            }
                        }
                    }
                }
            }
        }
    }

    // Metodo para obtener el valor.
    private fun obtenerValor(): Double {
        return if(inputCantidad.text.isNullOrBlank() || inputCantidad.text.toString().toDouble() == 0.00) {
            viewError.setText(R.string.errorValor)
            0.00
        } else {
            val input: Double = inputCantidad.text.toString().toDouble()
            viewError.text = ""
            input
        }
    }

    // Metodo para obtener el item seleccionado en el spinner1.
    private fun obtenerDatosSp1() {
        unidad1 = spUnidad1.selectedItemPosition
    }

    // Metodo para obrener el item seleccionado en el spinner2.
    private fun obtenerDatosSp2() {
        unidad2 = spUnidad2.selectedItemPosition
    }

    // Método para calcular la distancia, el peso y líquidos.
    private fun calculoUnidades(vlrInsertado: Double, unidad1: Int, unidad2: Int) {
        when {
            unidad1 == unidad2 -> {
                viewResultado.text = ("El resultado es: $vlrInsertado")
            }
            unidad1 > unidad2 -> {
                resultado = insertado*(10.00.pow(unidad1 - unidad2))
                viewResultado.text = ("El resultado es: $resultado")
                resultado = 0.00
            }
            else -> {
                var calcOp: Double
                var cantDivision : Double = insertado
                for(num in unidad1 until unidad2) {
                    calcOp = cantDivision / 10.00
                    cantDivision = calcOp
                }
                resultado = cantDivision
                viewResultado.text = ("El resultado es: $resultado")
                resultado = 0.00
            }
        }
    }

    // Metodo para calcular la temperatura.
    private fun calcularTemparatura(vlrInsertado: Double, unidad1: Int, unidad2: Int) {
        when {
            unidad1 == unidad2 -> {
                viewResultado.text = ("El resultado es: $vlrInsertado")
            }
            unidad1 < unidad2 -> {
                resultado = ((vlrInsertado * 9) / 5) + 32.00
                viewResultado.text = ("El resultado es: $resultado")
                resultado = 0.00
            }
            else -> {
                resultado = (((vlrInsertado - 32) * 5 ) / 9)
                resultado = String.format("%.3f", resultado).toDouble()
                viewResultado.text = ("El resultado es: $resultado")
                resultado = 0.00
            }
        }
    }

    // Metodo para calcular la capacidad de HDD.
    private fun calcularCapacidad(vlrInsertado: Double, unidad1: Int, unidad2: Int) {
        if(unidad1 == unidad2) {
            viewResultado.text = ("El resultado es: $vlrInsertado")
        } else if (unidad1 > unidad2) {
            if(unidad1 == 2 && unidad2 == 1){
                resultado = vlrInsertado * 8
                viewResultado.text = ("El resultado es: $resultado")
                resultado = 0.00
            } else if (unidad1 > 2 && unidad2 == 1) {
                resultado = (vlrInsertado * 8) * (1024.00.pow((unidad1 -1) - unidad2))
                viewResultado.text = ("El resultado es: $resultado")
                resultado = 0.00
            } else {
                resultado = vlrInsertado*(1024.00.pow(unidad1 - unidad2))
                viewResultado.text = ("El resultado es: $resultado")
                resultado = 0.00
            }
        } else {
            if(unidad1 == 1 && unidad2 == 2) {
                resultado = vlrInsertado / 8
                viewResultado.text = ("El resultado es: $resultado")
                resultado = 0.00
            } else if (unidad1 == 1 && unidad2 > 2) {
                var calOp: Double
                var cantDivision: Double = vlrInsertado
                for(num in unidad1..unidad2 -2) {
                    calOp = cantDivision / 1024.00
                    cantDivision = calOp
                }
                resultado = cantDivision / 8
                viewResultado.text = ("El resultado es: $resultado")
                resultado = 0.00
            } else {
                var calOp: Double
                var cantDivision: Double = vlrInsertado
                for(num in unidad1 until unidad2) {
                    calOp = cantDivision / 1024.00
                    cantDivision = calOp
                }
                resultado = cantDivision
                viewResultado.text = ("El resultado es: $resultado")
                resultado = 0.00
            }
        }
    }
}