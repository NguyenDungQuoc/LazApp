package com.example.lazapp

import android.app.Activity
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.lazapp.adapter.CartAdapter
import com.example.lazapp.adapter.SwipeToDeleteCallback
import com.example.lazapp.model.ForYouProduct
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_cart_for_you.*
import kotlinx.android.synthetic.main.bottom_menu_cart.*
import org.greenrobot.eventbus.EventBus

class CartForYouActivity : AppCompatActivity() {
    private var adapter: CartAdapter? = null
    private var sharedPre: SharedPreferences? = null
    private var prefsEditor: SharedPreferences.Editor? = null
    private var listCart: MutableList<ForYouProduct>? = null
    private var listIdCart: String? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_for_you)

        getItemFromSharedPrefer()
        textSumPrice.text = "${adapter?.getSumPrice()} đ"

        adapter?.onClick = {
            prefsEditor?.putString("LIST_CART", Gson().toJson((listCart)))
            prefsEditor?.apply()
            adapter?.notifyDataSetChanged()
            textSumPrice.text = "${adapter?.getSumPrice()} đ"
        }
    }

    private fun getItemFromSharedPrefer() {
        sharedPre = getSharedPreferences("APP_LAZADA", Activity.MODE_PRIVATE)
        prefsEditor = sharedPre?.edit()

        listIdCart = sharedPre?.getString("LIST_CART", "") ?: ""
        listCart = Gson().fromJson<MutableList<ForYouProduct>>(
            listIdCart,
            object : TypeToken<MutableList<ForYouProduct>>() {}.type
        ) ?: mutableListOf()

        recyclerViewCart.layoutManager = GridLayoutManager(this, 1)
        recyclerViewCart.setHasFixedSize(true)
        adapter = CartAdapter(baseContext, (listCart ?: mutableListOf()))
        recyclerViewCart.adapter = adapter
        ////lướt xóa
        val swipeHandler = object : SwipeToDeleteCallback() {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                listCart?.removeAt(position)///phải lấy list show item
                prefsEditor?.putString("LIST_CART", Gson().toJson((listCart)))
                prefsEditor?.apply()
                adapter?.notifyItemRemoved(position)
                textSumPrice.text = "${adapter?.getSumPrice()} đ"
            }


        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerViewCart)
    }

    override fun onDestroy() {
        EventBus.getDefault().post(adapter?.getSumOrder())
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun onClickButton(view: View) {
        when (view.id) {
            R.id.btnBackCart -> {
                onBackPressed()
            }
            R.id.btnDeleteCart -> {
                val listChecked = adapter?.getListCheckedProduct() ?: mutableListOf()
                for (index in ((listCart?.size ?: 0) - 1) downTo 0) {
                    val item = listCart?.getOrNull(index)
                    for (i in listChecked) {
                        if (i == item) {
                            listCart?.removeAt(index)
                            break

                        }
                    }
                }
                prefsEditor?.putString("LIST_CART", Gson().toJson((listCart)))
                prefsEditor?.apply()
                adapter?.removeCheckedProduct()
                textSumPrice.text = "${adapter?.getSumPrice()} đ"
            }

            R.id.btnPay -> {

            }

        }
    }

}