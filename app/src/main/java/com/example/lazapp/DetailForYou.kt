package com.example.lazapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.example.lazapp.model.ForYouProduct
import kotlinx.android.synthetic.main.actionbar_foryou_detail.*
import kotlinx.android.synthetic.main.detail_foryou_product.*
import kotlinx.android.synthetic.main.nav_menu_cart.*
import kotlinx.android.synthetic.main.row_foryou.*

class DetailForYou : AppCompatActivity() {
    private var menu: Menu? = null
    private var forYouItem: ForYouProduct? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_for_you)
        getDetailProduct()
        btnListCart.setOnClickListener {
            val intent: Intent = Intent(this@DetailForYou, CartForYou::class.java)
            startActivity(intent)
        }
        btnBack.setOnClickListener {
            onBackPressed()
        }
        btnHome.setOnClickListener {
            val intent: Intent = Intent(this@DetailForYou, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDetailProduct() {
        forYouItem = intent?.getParcelableExtra("DATA")
        Glide.with(imgForYouDetail.context).load(forYouItem?.itemImg).into(imgForYouDetail)
        tvPriceForYouDetail.text = "${forYouItem?.itemPrice}  đ"
        textNameForYouDetail.text = forYouItem?.itemTitle

        var format: String = String.format("%.1f", forYouItem?.itemRatingScore)

        textNumberRate.text = "${format} /5"

        ratingBarForYouDetail.rating = (forYouItem?.itemRatingScore ?: 0) as Float
    }

}