package com.example.lazapp

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.lazapp.adapter.ForYouAdapter
import com.example.lazapp.model.ForYouProduct
import com.example.lazapp.viewmodel.PromotionViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.actionbar_foryou_detail.*
import kotlinx.android.synthetic.main.actionbar_foryou_detail.textNumberCart
import kotlinx.android.synthetic.main.nav_menu_cart.*
import kotlinx.android.synthetic.main.nav_menu_home.*
import kotlinx.android.synthetic.main.row_detail_foryou.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DetailForYouActivity : AppCompatActivity() {
    private var adapter: ForYouAdapter? = null
    private var forYouItem: ForYouProduct? = null
    private var listLike: MutableList<String>? = null
    private var promotionViewModel: PromotionViewModel? = null
    private var listIdString: String? = null
    private var sharedPre: SharedPreferences? = null
    private var prefsEditor: SharedPreferences.Editor? = null
    private var listCart: MutableList<ForYouProduct>? = null
    private var listIdCart: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_for_you)

        promotionViewModel = ViewModelProviders.of(this).get(PromotionViewModel::class.java)
        getDetailProduct()
        getAllData()

        getDataFromDB()


        adapter = ForYouAdapter(baseContext, mutableListOf())
        promotionViewModel?.result?.observe(this, {
            val listItemForYou = it.result?.forYou
            listItemForYou?.let {
                adapter?.setData(listItemForYou)
            }
        })
    }

    private fun getDataFromDB() {
        sharedPre = getSharedPreferences("APP_LAZADA", Activity.MODE_PRIVATE)
        prefsEditor = sharedPre?.edit()
        //list Cart
        listIdCart = sharedPre?.getString("LIST_CART", "") ?: ""
        listCart = Gson().fromJson<MutableList<ForYouProduct>>(
            listIdCart,
            object : TypeToken<MutableList<ForYouProduct>>() {}.type
        ) ?: mutableListOf()
        ///list Favorite
        listIdString = sharedPre?.getString("LIST_ID", "") ?: ""
        listLike = Gson().fromJson<MutableList<String>>(
            listIdString,
            object : TypeToken<MutableList<String>>() {}.type
        ) ?: mutableListOf()

        val sharedPre: SharedPreferences = getSharedPreferences("APP_LAZADA", MODE_PRIVATE)
        sharedPre.getString("APP_LAZADA", "")
        for (id in (listLike ?: mutableListOf())) {
            if (id == forYouItem?.id) {
                forYouItem?.isLike = true
            }
        }
        if (forYouItem?.isLike == true) {
            val item = icLikeForYouDetail
            item.setBackgroundResource(R.drawable.ic_heart_click)
        }
    }

    private fun getAllData() {
        promotionViewModel?.getAllHomeData()
    }
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getSum(sum: Int?): Int?{
        textNumberCart.text = sum.toString()
        return sum
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    private fun getDetailProduct() {
        forYouItem = intent?.getParcelableExtra("DATA")
        Glide.with(imgForYouDetail.context).load(forYouItem?.itemImg).into(imgForYouDetail)
        tvPriceForYouDetail.text = "${forYouItem?.itemPrice}  đ"
        textNameForYouDetail.text = forYouItem?.itemTitle

        val format: String = String.format("%.1f", forYouItem?.itemRatingScore)

        textNumberRate.text = "${format} /5"

        ratingBarForYouDetail.rating = ((forYouItem?.itemRatingScore ?: 0) as? Float) ?: 0f

        textNumberCart.text = intent?.getStringExtra("NUMBER")
    }

    override fun onDestroy() {
        EventBus.getDefault().post(textNumberCart.text.toString().toIntOrNull())
        super.onDestroy()
    }

    fun onClickButton(view: View) {
        when (view.id) {
            R.id.btnListCart -> {
                val intent = Intent(this@DetailForYouActivity, CartForYouActivity::class.java)
                intent.putParcelableArrayListExtra(
                    "DATA",
                    ArrayList(promotionViewModel?.result?.value?.result?.forYou)
                )
                startActivity(intent)
            }
            R.id.btnBack -> {
                onBackPressed()
            }
            R.id.btnHome -> {
                val intent = Intent(this@DetailForYouActivity, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btnCart -> {
                forYouItem?.isSelected = true
                if(forYouItem?.isSelected == true){
                    if(forYouItem in (listCart ?: mutableListOf())){
                        forYouItem?.numberProductCart = forYouItem?.numberProductCart?.plus(1)
                    }else {
                        forYouItem?.let {
                            listCart?.add(it)
                        }
                    }
                }
                prefsEditor?.putString("LIST_CART", Gson().toJson((listCart)))
                prefsEditor?.apply()
                adapter?.notifyDataSetChanged()
                //get number form text
              var a= textNumberCart.text.toString().toInt()
                textNumberCart.text = a.plus(1).toString()
            }
            R.id.btnBuyNow -> {
            }
            R.id.icLikeForYouDetail -> {
                forYouItem?.isLike = !(forYouItem?.isLike ?: false)
                forYouItem?.id?.toInt()
                if (forYouItem?.isLike == true) {
                    view.setBackgroundResource(R.drawable.ic_heart_click)
                } else {
                    view.setBackgroundResource(R.drawable.ic_heart_home)
                }

                if (forYouItem?.isLike == true) {
                    listLike?.add((forYouItem?.id ?: 0) as String)
                } else {
                    for (id in (listLike ?: mutableListOf())) {
                        if (id == forYouItem?.id) {
                            listLike?.remove(id)
                            break
                        }
                    }
                }
                prefsEditor?.putString("LIST_ID", Gson().toJson((listLike)))
                prefsEditor?.apply()
            }
        }


    }
}