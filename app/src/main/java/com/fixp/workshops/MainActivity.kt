package com.fixp.workshops

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

class MainActivity : AppCompatActivity() {

    private lateinit var signIn: Button
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signIn = findViewById(R.id.sign_in)
        callbackManager = CallbackManager.Factory.create()

        val accessToken = AccessToken.getCurrentAccessToken()

        if (accessToken != null && !accessToken.isExpired) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        signIn.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "email"))
        }

        val facebookCallback = object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                // Manejar cancelación de inicio de sesión
            }

            override fun onError(error: FacebookException) {
                // Manejar error de inicio de sesión
            }

            override fun onSuccess(result: LoginResult) {
                // Manejar inicio de sesión exitoso
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            }
        }

        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
