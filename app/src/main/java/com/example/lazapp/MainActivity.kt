package com.example.lazapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lazapp.`interface`.RetrofitInterface
import com.example.lazapp.adapter.FlashSaleAdapter
import com.example.lazapp.adapter.TrendingAdapter
import com.example.lazapp.adapter.ViewPageAdapter
import com.example.lazapp.viewmodel.PromotionViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.flash_sale_container.*
import kotlinx.android.synthetic.main.trending_container.*

class MainActivity : AppCompatActivity() {
    private var mInterface: RetrofitInterface? = null
    private var pagerAdapter: ViewPageAdapter? = null
    private var flashSaleAdapter:FlashSaleAdapter? = null
    private var trendingAdapter: TrendingAdapter? = null
    private var promotionViewModel: PromotionViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        promotionViewModel = ViewModelProviders.of(this).get(PromotionViewModel::class.java)
        //flashsale product
        recyclerViewFlashSale.layoutManager = GridLayoutManager(this, 3 )
        recyclerViewFlashSale.setHasFixedSize(true)
        flashSaleAdapter = FlashSaleAdapter(baseContext, mutableListOf())
        trendingAdapter = TrendingAdapter(baseContext, mutableListOf())
        promotionViewModel?.result?.observe(this, {
            val listItemResponse = it.result?.flashSale
            listItemResponse?.let {
                flashSaleAdapter?.setData(listItemResponse)
            }
        })
        recyclerViewFlashSale.adapter = flashSaleAdapter
        //trending product
        recyclerViewTrending.layoutManager = GridLayoutManager(this, 3 )
        recyclerViewTrending.setHasFixedSize(true)
        promotionViewModel?.result?.observe(this, {
            val listItemTrending = it.result?.trending
            listItemTrending?.let {
                trendingAdapter?.setData(listItemTrending)
            }
        })
        recyclerViewTrending.adapter = trendingAdapter
        //
        resultOfPromotion()

        getAllData()
    }
    private fun resultOfPromotion(){
        promotionViewModel?.result?.observe(this, Observer {
            pagerAdapter = ViewPageAdapter(
                supportFragmentManager,
                it.result?.promotion
            )
            viewPager.adapter = pagerAdapter
        })
        pageIndicatorView.count = 5; // specify total count of indicators
        pageIndicatorView.selection = 2;

        promotionViewModel?.errorMessage?.observe(this, {
            val builder = AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Không thể tải dữ liệu")
                .setPositiveButton("OK",DialogInterface.OnClickListener { dialogInterface, i -> finish() })
                .setIcon(R.drawable.ic_android_black_24dp)
                .create()
                .show()
        })
    }

    private fun getAllData() {
        promotionViewModel?.getAllHomeData()
    }

}