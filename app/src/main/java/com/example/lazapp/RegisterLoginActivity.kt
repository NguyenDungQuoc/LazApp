package com.example.lazapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class RegisterLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_login)
    }

    fun onClickButton(view: View){
        when(view.id) {
            R.id.btnLogin -> {
                val intent = Intent(this@RegisterLoginActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

    }
}