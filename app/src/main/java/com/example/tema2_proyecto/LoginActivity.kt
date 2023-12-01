package com.example.tema2_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    // Constantes de usuario y contraseña
    private val MYUSER = "user"
    private val MYPASS = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val validarButton: Button = findViewById(R.id.validarButton)

        validarButton.setOnClickListener {
            val inputUser = usernameEditText.text.toString()
            val inputPass = passwordEditText.text.toString()

            // Verificar credenciales
            if (inputUser == MYUSER && inputPass == MYPASS) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra("USERNAME", inputUser)
                intent.putExtra("PASSWORD", inputPass)
                startActivity(intent)
            } else {
                Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
