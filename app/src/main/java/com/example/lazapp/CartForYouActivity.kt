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

class CartForYouActivity : AppCompatActivity() {
    private var adapter: CartAdapter? = null
    private var sharedPre: SharedPreferences? = null
    private var prefsEditor: SharedPreferences.Editor? = null
    private var listCart: MutableList<String>? = null
    private var listIdCart: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_for_you)

        getItemFromSharedPrefer()

    }

    private fun getItemFromSharedPrefer() {
        sharedPre = getSharedPreferences("APP_LAZADA", Activity.MODE_PRIVATE)
        prefsEditor = sharedPre?.edit()
        listIdCart = sharedPre?.getString("LIST_ID_CART", "") ?: ""
        listCart = Gson().fromJson<MutableList<String>>(
            listIdCart,
            object : TypeToken<MutableList<String>>() {}.type
        ) ?: mutableListOf()
        val listItem: MutableList<ForYouProduct> =
            intent?.getParcelableArrayListExtra("DATA") ?: mutableListOf()
        val listResult: MutableList<ForYouProduct> = mutableListOf()
        for (i in listItem) {
            for (id in (listCart ?: mutableListOf())) {
                if (id == i.id) {
                    listResult.add(i)
                }
            }
        }
        recyclerViewCart.layoutManager = GridLayoutManager(this, 1)
        recyclerViewCart.setHasFixedSize(true)
        adapter = CartAdapter(baseContext, listResult)
        recyclerViewCart.adapter = adapter
        ////lướt xóa
        val swipeHandler = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                val item = listResult[position]

                val id = item.id

                listResult.removeAt(position)///phải lấy list show item
//                adapter?.notifyDataSetChanged()
                for (index in ((listCart?.size?.minus(1)))?.downTo(0)!!) {
//                for (index in ((listCart?.size - 1) downTo 0)) {
                    val item = listCart?.getOrNull(index) ?: break
                    if (item == id) {
                        listCart?.removeAt(index)
                    }
                }
                prefsEditor?.putString("LIST_ID_CART", Gson().toJson((listCart)))
                prefsEditor?.apply()
                adapter?.notifyItemRemoved(position)

            }


        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerViewCart)
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
                        if (i.id == item) {
                            listCart?.removeAt(index)
                            break
                        }
                    }

                }
                prefsEditor?.putString("LIST_ID_CART", Gson().toJson((listCart)))
                prefsEditor?.apply()
                adapter?.removeCheckedProduct()
            }

            R.id.btnPay -> {
                textSumPrice.text = adapter?.getSumPrice() + getString(R.string.VND)
            }

        }
    }

}