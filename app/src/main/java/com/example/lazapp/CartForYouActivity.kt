package com.example.lazapp

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lazapp.adapter.CartAdapter
import com.example.lazapp.model.ForYouProduct
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.actionbar_cart.*
import kotlinx.android.synthetic.main.activity_cart_for_you.*

class CartForYouActivity : AppCompatActivity() {
    private var adapter: CartAdapter? = null
    private var sharedPre: SharedPreferences? = null
    private var prefsEditor: SharedPreferences.Editor? = null
    private var listCart: MutableList<String>? = null
    private var forYouItem: ForYouProduct? = null
    private var listIdCart: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_for_you)

        recyclerViewCart.layoutManager = GridLayoutManager(this, 1)
        recyclerViewCart.setHasFixedSize(true)

        btnDeleteCart.setOnClickListener {
            var listChecked = adapter?.getListCheckedProduct() ?: mutableListOf()
            for(index in ((listCart?.size ?: 0) - 1) downTo 0 )  {
                val  item = listCart?.getOrNull(index)
                for (i in listChecked){
                    if (i.id ==  item){
                        listCart?.removeAt(index)
                        break
                    }
                }

            }
            prefsEditor?.putString("LIST_ID_CART", Gson().toJson((listCart)))
            prefsEditor?.apply()
            adapter?.removeCheckedProduct()

        }


        sharedPre = getSharedPreferences("APP_LAZADA", Activity.MODE_PRIVATE)
        prefsEditor = sharedPre?.edit()
        listIdCart = sharedPre?.getString("LIST_ID_CART", "") ?: ""
        listCart = Gson().fromJson<MutableList<String>>(
            listIdCart,
            object : TypeToken<MutableList<String>>() {}.type
        ) ?: mutableListOf()
        val listItem: MutableList<ForYouProduct> =
            intent?.getParcelableArrayListExtra<ForYouProduct>("DATA") ?: mutableListOf()
        val listResult: MutableList<ForYouProduct> = mutableListOf()
//        var sharedPre: SharedPreferences = getSharedPreferences("APP_LAZADA", MODE_PRIVATE)
//        val productJsonArray: String? = sharedPre?.getString("APP_LAZADA", "")
        for (i in listItem) {
            for (id in (listCart ?: mutableListOf())) {
                if (id == i.id) {
                    listResult.add(i)
                }
            }
        }
        adapter = CartAdapter(baseContext, listResult)
        recyclerViewCart.adapter = adapter

    }

    fun onClickButton(view: View){
        when(view.id) {
            R.id.btnBackCart -> {
                onBackPressed()
            }
//            R.id.btnDeleteCart -> {
//                Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show()
//                //goi ham lay item da check tu mang
//                var listChecked = adapter?.getListCheckedProduct() ?: mutableListOf()
//
//                adapter?.removeCheckedProduct()
//            }
        }
    }

}