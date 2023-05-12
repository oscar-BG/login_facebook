package com.fixp.workshops

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager

class HomeActivity : AppCompatActivity() {

    private lateinit var fbImage:ImageView

    private lateinit var fbName:TextView

    private lateinit var fbId:TextView

    private lateinit var fbEmail:TextView

    private lateinit var fbLogout:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fbImage = findViewById(R.id.fb_image)
        fbName = findViewById(R.id.fb_name)
        fbId = findViewById(R.id.fb_id)
        fbLogout = findViewById(R.id.fb_log_out)

        val accessToken = AccessToken.getCurrentAccessToken()

        val request = GraphRequest.newMeRequest(accessToken) { `object`, response->
            val id=`object`?.getString("id")
            val email=`object`?.getString("email")
            val fullName=`object`?.getString("name")

            val profileUrl=`object`?.getJSONObject("picture")
                ?.getJSONObject("data")?.getString("url")

            fbName.text=fullName
            fbId.text=id
            fbEmail.text=email
            Glide.with(applicationContext).load(profileUrl).into(fbImage)
        }

        val parameters=Bundle()
        parameters.putString("field", "id,name,link,picture,type(large),email")
        request.parameters=parameters
        request.executeAsync()

        fbLogout.setOnClickListener {
            LoginManager.getInstance().logOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}