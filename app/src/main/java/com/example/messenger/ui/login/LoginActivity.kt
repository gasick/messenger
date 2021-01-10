package com.example.messenger.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.messenger.R
import com.example.messenger.ui.signup.SignUpActivity
import com.example.messenger.ui.data.local.AppPreferences

class LoginActivity : AppCompatActivity(), LoginView, View.OnClickListener {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp:Button
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: LoginPresenter
    private lateinit var preferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bindViews()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setUsernameError() {
        etUsername.error = "Username field cannot be empty"
    }

    override fun setPasswordError() {
        etPassword.error = "Password field cannot be empty"
    }

    override fun navigateToSingUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun navigatetoHome() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun bindViews() {
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_sign_up)
        progressBar = findViewById(R.id.progress_bar)
        btnLogin.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)
    }


    override fun showAuthError(){
        Toast.makeText(this, "Invalid username or password cobmination.", Toast.LENGTH_LONG).show()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_login){
            presenter.executeLogin(
                etUsername.text.toString(),
                etPassword.text.toString()
            )
        } else if (view.id == R.id.btn_sign_up){
            navigateToSingUp()
        }
    }

    override fun getContext(): Context {
        return this
    }
}
