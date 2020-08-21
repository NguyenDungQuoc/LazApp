package com.example.lazapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lazapp.`interface`.RetrofitInterface
import com.example.lazapp.adapter.FlashSaleAdapter
import com.example.lazapp.adapter.ForYouAdapter
import com.example.lazapp.adapter.TrendingAdapter
import com.example.lazapp.adapter.ViewPageAdapter
import com.example.lazapp.model.ProductModel
import com.example.lazapp.model.ProductResult
import com.example.lazapp.viewmodel.PromotionViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.flash_sale_container.*
import kotlinx.android.synthetic.main.foryou_container.*
import kotlinx.android.synthetic.main.trending_container.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mInterface: RetrofitInterface? = null
    private var pagerAdapter: ViewPageAdapter? = null
    private var flashSaleAdapter: FlashSaleAdapter? = null
    private var trendingAdapter: TrendingAdapter? = null
    private var forYouAdapter: ForYouAdapter? = null
    private var promotionViewModel: PromotionViewModel? = null

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

            viewPager.postDelayed(Runnable {
                kotlin.run { viewPager.currentItem = 5 }
            }, 8)


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
    }

    private fun resultOfPromotion() {
        promotionViewModel?.result?.observe(this, Observer {
            pagerAdapter = ViewPageAdapter(
                supportFragmentManager,
                it.result?.promotion
            )
            viewPager.adapter = pagerAdapter
        })
        pageIndicatorView.count = 5; // specify total count of indicators
        pageIndicatorView.selection = 2;
//        viewPager.currentItem =
//            ((viewPager?.currentItem?.plus(1))?.rem((viewPager?.childCount?.plus(1)!!))!!)
        promotionViewModel?.errorMessage?.observe(this, {
            val builder = AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Không thể tải dữ liệu")
                .setPositiveButton(
                    "OK",
                    DialogInterface.OnClickListener { dialogInterface, i -> finish() })
                .setIcon(R.drawable.ic_android_black_24dp)
                .create()
                .show()
        })
    }

    private fun getAllData() {
        promotionViewModel?.getAllHomeData()
    }

    /**
     * viết hàm scroll viewpager đến vi trí chỉ định
     * dùng Handler để goi tới hàm scroll viewpager sau 1 khoảng thời gian chỉ định
     */
//    private var timer: Timer? = null
//    private fun createSlideShow() {
//
//        var currentPosition: Int = 0
//        var handler: Handler = Handler()
//
//        val runnable = Runnable {
//            if (currentPosition == promotionViewModel?.result?.value?.result?.promotion?.size) {
//                currentPosition = 0
//                viewPager.setCurrentItem(currentPosition++, true)
//            }
//        }
//
//        timer = Timer()
//        timer!!.schedule(object : TimerTask() {
//            override fun run() {
//                handler.post(runnable)
//            }
//        }, 250, 2500)
//    }
}