package com.example.tema2_proyecto

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class CallActivity : AppCompatActivity() {

    private lateinit var botonEmergencias: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.call_activity)

        // Mueve esta línea después de setContentView
        botonEmergencias = findViewById(R.id.botonEmergencias)

        botonEmergencias.setOnClickListener {
            checkAndMakePhoneCall()
        }
    }

    private fun checkAndMakePhoneCall() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            makePhoneCall()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                makePhoneCall()
            } else {
                // El usuario denegó el permiso, podrías mostrar un mensaje o tomar alguna acción.
            }
        }

    private fun makePhoneCall() {
        val emergencyNumber = "tel:911"

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED &&
            packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)
        ) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse(emergencyNumber)
            startActivity(callIntent)
        }
    }
}
