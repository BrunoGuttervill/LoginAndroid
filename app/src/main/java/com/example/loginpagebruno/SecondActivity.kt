package com.example.loginpagebruno

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        saveButton = findViewById(R.id.saveButton)

        saveButton.isEnabled = false

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveButton.isEnabled = usernameInput.text.isNotEmpty() && passwordInput.text.isNotEmpty()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        usernameInput.addTextChangedListener(watcher)
        passwordInput.addTextChangedListener(watcher)

        saveButton.setOnClickListener {
            saveCredentials()
        }
    }

    private fun saveCredentials() {
        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()

        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).edit()

        prefs.putString(username, "$password|user")
        prefs.apply()

        AlertDialog.Builder(this)
            .setMessage("Credenciais atualizadas com sucesso!")
            .setPositiveButton("OK") { _, _ -> finish() }
            .show()
    }
}
