package com.example.lazapp

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.lazapp.adapter.FlashSaleAdapter
import com.example.lazapp.adapter.ForYouAdapter
import com.example.lazapp.adapter.TrendingAdapter
import com.example.lazapp.adapter.ViewPageAdapter
import com.example.lazapp.model.ForYouProduct
import com.example.lazapp.viewmodel.PromotionViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.container_flash_sale.*
import kotlinx.android.synthetic.main.container_foryou.*
import kotlinx.android.synthetic.main.container_trending.*
import kotlinx.android.synthetic.main.nav_menu_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {
    private var pagerAdapter: ViewPageAdapter? = null
    private var flashSaleAdapter: FlashSaleAdapter? = null
    private var trendingAdapter: TrendingAdapter? = null
    private var forYouAdapter: ForYouAdapter? = null
    private var promotionViewModel: PromotionViewModel? = null
    private var sharedPre: SharedPreferences? = null
    private var prefsEditor: SharedPreferences.Editor? = null
    private var listCart: MutableList<ForYouProduct>? = null
    private var stringIdCart: String? = null

    private var mhandle: Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        promotionViewModel = ViewModelProviders.of(this).get(PromotionViewModel::class.java)

        getTextNumberCartFromDB()

        flashSale()
        trending()
        forYou()
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
        swipeRefresh.setOnRefreshListener {
            getAllData()
            swipeRefresh.isRefreshing = false
        }

    }

    private fun getTextNumberCartFromDB() {
        sharedPre = getSharedPreferences("APP_LAZADA", Activity.MODE_PRIVATE)
        prefsEditor = sharedPre?.edit()
        //list Cart
        stringIdCart = sharedPre?.getString("LIST_CART", "") ?: ""
        listCart = Gson().fromJson<MutableList<ForYouProduct>>(
            stringIdCart,
            object : TypeToken<MutableList<ForYouProduct>>() {}.type
        ) ?: mutableListOf()
        var sumNum = 0
        for(item in (listCart ?: mutableListOf())){
            sumNum += (item.numberProductCart ?: 0)
        }
        textNumberCart.text = sumNum.toString()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getSum(sum: Int?){
        textNumberCart.text = sum.toString()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
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


    private fun forYou() {
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
            val intent = Intent(this@MainActivity, DetailForYouActivity::class.java)
            intent.putExtra("DATA", it)
            intent.putExtra("NUMBER",textNumberCart.text)
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

    private fun flashSale() {
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
            AlertDialog.Builder(this)
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


    fun onClickButton(view: View) {
        when(view.id){
            R.id.lnLike -> {
                val intent = Intent(this@MainActivity, ListFavoriteActivity::class.java)
                intent.putParcelableArrayListExtra("DATA", ArrayList(promotionViewModel?.result?.value?.result?.forYou) )
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            }

            R.id.lnCart -> {
                val intent = Intent(this@MainActivity, CartForYouActivity::class.java)
                intent.putParcelableArrayListExtra("DATA", ArrayList(promotionViewModel?.result?.value?.result?.forYou) )
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            }
        }
    }

}