package com.example.lazapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar

class CartForYou : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_for_you)

        val actionBar: ActionBar? = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = " Giỏ hàng của tôi"
        title = actionBar?.title

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return true
    }
}