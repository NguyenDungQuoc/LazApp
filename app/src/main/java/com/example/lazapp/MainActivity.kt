package com.example.lazapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.lazapp.`interface`.RetrofitInterface
import com.example.lazapp.adapter.ViewPageAdapter
import com.example.lazapp.viewmodel.PromotionViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mInterface: RetrofitInterface? = null
    private var pagerAdapter: ViewPageAdapter? = null
    private var promotionViewModel: PromotionViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        promotionViewModel = ViewModelProviders.of(this).get(PromotionViewModel::class.java)

        promotionViewModel?.result?.observe(this, Observer {
            pagerAdapter = ViewPageAdapter(
                supportFragmentManager,
                it.result.promotion
            )
            viewPager.adapter = pagerAdapter
        })
        pageIndicatorView.count = 5; // specify total count of indicators
        pageIndicatorView.selection = 2;
        getAllData()
    }

    private fun getAllData() {
        promotionViewModel?.getAllHomeData()
    }

}