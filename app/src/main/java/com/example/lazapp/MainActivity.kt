package com.example.lazapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.lazapp.adapter.FlashSaleAdapter
import com.example.lazapp.adapter.ForYouAdapter
import com.example.lazapp.adapter.TrendingAdapter
import com.example.lazapp.adapter.ViewPageAdapter
import com.example.lazapp.viewmodel.PromotionViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.flash_sale_container.*
import kotlinx.android.synthetic.main.foryou_container.*
import kotlinx.android.synthetic.main.trending_container.*

class MainActivity : AppCompatActivity() {
    private var pagerAdapter: ViewPageAdapter? = null
    private var flashSaleAdapter: FlashSaleAdapter? = null
    private var trendingAdapter: TrendingAdapter? = null
    private var forYouAdapter: ForYouAdapter? = null
    private var promotionViewModel: PromotionViewModel? = null

    private var mhandle: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        promotionViewModel = ViewModelProviders.of(this).get(PromotionViewModel::class.java)
        //flashsale product
        flashsale()
        //trending product
        trending()
        //Foryouproduct
        foryou()
        resultOfPromotion()

        getAllData()

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                mhandle?.removeCallbacks(mRunnable)//2 . delete luong runnable
                mhandle?.postDelayed(mRunnable, 3000) //3 runnable auto chay lại
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    var mRunnable: Runnable = Runnable {
        var currentPage = viewPager.currentItem
        //reverse pagecurrent ve page 0


        currentPage =
            if (currentPage.plus(1) >= promotionViewModel?.result?.value?.result?.promotion?.size!!) {
                0
            } else {
                currentPage.plus(1)
            }
        viewPager.currentItem = currentPage
    }

    private fun foryou() {
        recyclerViewForYou.layoutManager = GridLayoutManager(this, 2)
        recyclerViewForYou.setHasFixedSize(true)
        forYouAdapter = ForYouAdapter(baseContext, mutableListOf())
        promotionViewModel?.result?.observe(this, {
            val listItemForYou = it.result?.forYou
            listItemForYou?.let {
                forYouAdapter?.setData(listItemForYou)
            }
        })
        recyclerViewForYou.adapter = forYouAdapter
        //click item
        forYouAdapter?.onClick = {
            val intent = Intent(this@MainActivity, DetailForYou::class.java)
            intent.putExtra("DATA", it)
            startActivity(intent)

        }
    }

    private fun trending() {
        recyclerViewTrending.layoutManager =
            GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)
        recyclerViewTrending.setHasFixedSize(true)
        trendingAdapter = TrendingAdapter(baseContext, mutableListOf())
        promotionViewModel?.result?.observe(this, {
            val listItemTrending = it.result?.trending
            listItemTrending?.let {
                trendingAdapter?.setData(listItemTrending)
            }
        })
        recyclerViewTrending.adapter = trendingAdapter
    }

    private fun flashsale() {
        recyclerViewFlashSale.layoutManager =
            GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)
        recyclerViewFlashSale.setHasFixedSize(true)
        flashSaleAdapter = FlashSaleAdapter(baseContext, mutableListOf())
        promotionViewModel?.result?.observe(this, {
            val listItemResponse = it.result?.flashSale
            listItemResponse?.let {
                flashSaleAdapter?.setData(listItemResponse)
            }
        })
        recyclerViewFlashSale.adapter = flashSaleAdapter
        //click item


    }

    private fun resultOfPromotion() {
        promotionViewModel?.result?.observe(this,  {
            pagerAdapter = ViewPageAdapter(
                supportFragmentManager,
                it.result?.promotion
            )
            viewPager.adapter = pagerAdapter
            mhandle?.postDelayed(mRunnable, 3000)//1 auto scroll 1 page
        })
        pageIndicatorView.count = 5
        pageIndicatorView.selection = 2

        promotionViewModel?.errorMessage?.observe(this, {
            val builder = AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Không thể tải dữ liệu")
                .setPositiveButton(
                    "OK"
                ) { _, _ -> finish() }
                .setIcon(R.drawable.ic_android_black_24dp)
                .create()
                .show()
        })
    }

    private fun getAllData() {
        promotionViewModel?.getAllHomeData()
    }


}