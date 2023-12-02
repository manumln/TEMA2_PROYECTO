package com.example.tema2_proyecto

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var botonLlamada: ImageButton
    private lateinit var botonAlarma: ImageButton
    private lateinit var botonNavegador: ImageButton
    private lateinit var botonMusica: ImageButton
    private lateinit var botonCartas : ImageButton
    private lateinit var botonChistes : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        botonLlamada = findViewById(R.id.botonLlamada)
        botonAlarma = findViewById(R.id.botonAlarma)
        botonNavegador = findViewById(R.id.botonNavegador)
        botonMusica = findViewById(R.id.botonMusica)
        botonCartas = findViewById(R.id.botonCartas)
        botonChistes = findViewById(R.id.botonChistes)

        initEvent()
    }

    private fun initEvent() {
        botonLlamada.setOnClickListener {
            val intent = Intent(this, CallActivity::class.java)
            startActivity(intent)
        }

        botonAlarma.setOnClickListener {
            Toast.makeText(this@MainActivity, "Pulsado boton Alarma", Toast.LENGTH_SHORT).show()
            crearAlarma()
        }

        botonNavegador.setOnClickListener {
            Log.d("MainActivity", "Clic en el botón de URL")
            val url = "https://www.google.es"
            openWebPage(url)
        }
        botonMusica.setOnClickListener {
            abrirSpotify("spotify:track:6nK2pIKFcRc5frrZKHgsiT");
        }
        botonCartas.setOnClickListener {
            val intent = Intent(this, CartasActivity::class.java)
            startActivity(intent)
        }
        botonChistes.setOnClickListener {
            val intent = Intent(this, ChistesActivty::class.java)
            startActivity(intent)
        }

    }
    private fun abrirSpotify(url: String) {
        val spotifyDeepLink = Uri.parse(url)

        //
        val uri = Uri.parse(spotifyDeepLink.toString())
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d("MainActivity", "Spotify no está instalado en el dispositivo.")
        }
    }
    private fun crearAlarma() {
        val alarma = Calendar.getInstance()
        alarma.add(Calendar.MINUTE, 2) // Add 2 minutes to the current time

        val intent = Intent(AlarmClock.ACTION_SET_ALARM)
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma en 2 minutos")
        intent.putExtra(AlarmClock.EXTRA_HOUR, alarma.get(Calendar.HOUR_OF_DAY))
        intent.putExtra(AlarmClock.EXTRA_MINUTES, alarma.get(Calendar.MINUTE))

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No se puede crear la alarma", Toast.LENGTH_SHORT).show()
        }
    }


    private fun openWebPage(url: String) {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        TODO("Not yet implemented")
    }
}