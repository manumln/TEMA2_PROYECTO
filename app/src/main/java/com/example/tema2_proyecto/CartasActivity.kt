package com.example.tema2_proyecto

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tema2_proyecto.databinding.ActivityCartasBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class CartasActivity : AppCompatActivity() {
    private lateinit var bindingMain: ActivityCartasBinding
    private var sum: Int = 0
    private lateinit var handler: Handler
    private lateinit var cardValues: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityCartasBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        handler = Handler(Looper.getMainLooper())
        initEvent()
        bindingMain.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun initEvent() {
        bindingMain.txtResultado.visibility = View.INVISIBLE
        bindingMain.btnBarajear.setOnClickListener {
            bindingMain.txtResultado.visibility = View.VISIBLE
            sum = 0 // Reinicia la suma al hacer clic en el botón
            game()
        }
    }

    private fun game() {
        sheduleRun()
    }

    private fun sheduleRun() {
        val schedulerExecutor = Executors.newSingleThreadScheduledExecutor()
        val msc = 1000

        for (i in 1..5) {
            schedulerExecutor.schedule({
                handler.post {
                    throwDadoInTime()
                }
            }, msc * i.toLong(), TimeUnit.MILLISECONDS)
        }

        schedulerExecutor.schedule({
            handler.post {
                viewResult()
            }
        }, msc * 7.toLong(), TimeUnit.MILLISECONDS)

        schedulerExecutor.shutdown()
    }

    private fun throwDadoInTime() {
        val numDados = IntArray(3) { Random.nextInt(1, 6) }
        cardValues = numDados.clone()

        val imagViews: Array<ImageView> = arrayOf(
            bindingMain.imagviewCard1,
            bindingMain.imagviewCard2,
            bindingMain.imagviewCard3
        )

        for (i in 0 until 3) {
            handler.post {
                selectView(imagViews[i], numDados[i])
            }
        }
    }

    private fun selectView(imgV: ImageView, v: Int) {
        when (v) {
            1 -> imgV.setImageResource(R.drawable.carta1)
            2 -> imgV.setImageResource(R.drawable.carta2)
            3 -> imgV.setImageResource(R.drawable.carta3)
            4 -> imgV.setImageResource(R.drawable.carta4)
            5 -> imgV.setImageResource(R.drawable.carta5)
            6 -> imgV.setImageResource(R.drawable.carta6)
        }
    }

    private fun viewResult() {
        sum += cardValues.sum()
        bindingMain.txtResultado.text = sum.toString()
        println("Cartas: ${cardValues.joinToString()} Suma: $sum")
    }
}
