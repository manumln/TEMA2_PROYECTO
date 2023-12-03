package com.example.tema2_proyecto

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tema2_proyecto.databinding.ActivityChistesBinding
import java.util.Locale

class ChistesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChistesBinding
    private lateinit var textToSpeech: TextToSpeech
    private val TOUCH_MAX_TIME = 500
    private var touchLastTime: Long = 0
    private lateinit var handler: Handler
    val MYTAG = "LOGCAT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChistesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureTextToSpeech()
        initHander()
        initEvent()

        // Configura el listener para el botón de retroceso
        binding.btnBack.setOnClickListener {
            finish() // Cierra la actividad actual y retrocede a la anterior
        }
    }

    private fun initHander() {
        handler = Handler(Looper.getMainLooper())
        binding.progressBar.visibility = View.VISIBLE
        binding.btnExample.visibility = View.GONE

        Thread {
            Thread.sleep(3000)
            handler.post {
                binding.progressBar.visibility = View.GONE
                val description = getString(R.string.describe).toString()
                Thread.sleep(4000)
                Log.i(MYTAG, "Se ejecuta correctamente el hilo")
                binding.btnExample.visibility = View.VISIBLE
                speakMeDescription(description)
            }
        }.start()
    }

    private fun configureTextToSpeech() {
        textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            if (it != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.getDefault()
                Log.i(MYTAG, "Sin problemas en la configuración TextToSpeech")
            } else {
                Log.i(MYTAG, "Error en la configuración TextToS 6ymn  /h")
            }
        })
    }

    private fun initEvent() {
        binding.btnExample.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - touchLastTime < TOUCH_MAX_TIME) {
                // Doble toque
                executorDoubleTouch()
            } else {
                // Toque simple
                speakRandomJoke()
            }

            touchLastTime = currentTime
        }
    }

    private fun speakRandomJoke() {
        val randomJoke = ChistesManager.chiste.random()
        speakMeDescription(randomJoke)
    }

    private fun executorDoubleTouch() {
        val chiste = resources.getString(R.string.chiste)
        speakMeDescription(chiste)
    }

    private fun speakMeDescription(s: String) {
        textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}
