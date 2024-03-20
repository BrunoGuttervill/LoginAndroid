package com.example.loginpagebruno

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var userEdit: EditText
    private lateinit var passEdit: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        setupListeners()
    }

    private fun initView() {
        userEdit = findViewById(R.id.usernameEditText)
        passEdit = findViewById(R.id.passwordEditText)
        loginBtn = findViewById(R.id.loginButton)
        loginBtn.isEnabled = false
    }

    private fun setupListeners() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginBtn.isEnabled = userEdit.text.isNotEmpty() && passEdit.text.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        userEdit.addTextChangedListener(watcher)
        passEdit.addTextChangedListener(watcher)

        loginBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val prefs = getSharedPreferences("LoginCredentials", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "user")
        val password = prefs.getString("password", "1234")

        if (userEdit.text.toString() == username && passEdit.text.toString() == password) {
            startActivity(Intent(this, SecondActivity::class.java))
        } else {
            AlertDialog.Builder(this)
                .setMessage("Username ou Password inválidos")
                .setPositiveButton("OK", null)
                .show()
        }
    }
}
