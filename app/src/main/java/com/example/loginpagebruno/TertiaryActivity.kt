package com.example.loginpagebruno

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class TertiaryActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var userTypeSpinner: Spinner
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tertiary)

        initView()
        setupListeners()
        setupUserTypeSpinner()
    }

    private fun initView() {
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        userTypeSpinner = findViewById(R.id.userTypeSpinner)
        registerButton = findViewById(R.id.registerButton)
        registerButton.isEnabled = false
    }

    private fun setupListeners() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                registerButton.isEnabled = usernameEditText.text.isNotEmpty() &&
                        passwordEditText.text.isNotEmpty() &&
                        confirmPasswordEditText.text.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        usernameEditText.addTextChangedListener(watcher)
        passwordEditText.addTextChangedListener(watcher)
        confirmPasswordEditText.addTextChangedListener(watcher)

        registerButton.setOnClickListener {
            if (validateInputs()) {
                registerUser()
            }
        }
    }

    private fun validateInputs(): Boolean {
        if (passwordEditText.text.toString() != confirmPasswordEditText.text.toString()) {
            showErrorDialog("As senhas não coincidem.")
            return false
        }
        return true
    }

    private fun registerUser() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        val userType = userTypeSpinner.selectedItem.toString()
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        if (prefs.contains(username)) {
            showErrorDialog("O nome de usuário já existe.")
            return
        }

        with(prefs.edit()) {
            putString(username, "$password|$userType")
            apply()
        }

        AlertDialog.Builder(this)
            .setMessage("Usuário cadastrado com sucesso!")
            .setPositiveButton("OK") { dialog, which -> finish() }
            .show()
    }

    private fun setupUserTypeSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.user_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            userTypeSpinner.adapter = adapter
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
