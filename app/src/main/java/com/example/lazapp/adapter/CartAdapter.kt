package com.example.lazapp.adapter

import android.content.Context
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lazapp.R
import com.example.lazapp.model.ForYouProduct
import kotlinx.android.synthetic.main.row_cart.*
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_foryou.view.*

class CartAdapter(
    private val context: Context,
    private var _foryou: MutableList<ForYouProduct>
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var productForYou: MutableList<ForYouProduct> = _foryou

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            init {
                itemView.checkBox.setOnClickListener {
                    var position = adapterPosition
                    var item = productForYou[position]
                    item.isCheck = true
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_cart, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productFY = productForYou.getOrNull(position)
        holder.itemView.apply {
            Glide.with(imgCart.context).load(productFY?.itemImg)
                .into(imgCart)
            tvNameCart.text = productFY?.itemTitle
            tvDiscountPercent.text = "-${productFY?.itemDiscount}"
            tvPriceDiscountCart.text = "${productFY?.itemDiscountPrice} đ"
            if (productFY?.itemPrice != "") {
                tvPriceCart.text = "${productFY?.itemPrice} đ"
                tvPriceCart.paintFlags =
                    tvPriceCart.paintFlags or TextPaint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvPriceCart.text = ""
            }
//            if(productFY?.isCheck == true){
//                checkBox.isChecked = true
//            }else {
//                checkBox.isChecked = false
//            }
            checkBox.isChecked = productFY?.isCheck == true
        }

    }

    override fun getItemCount() = productForYou?.size

//    fun getListCheckedProduct(): MutableList<ForYouProduct> {
//
//           for(id in _foryou.size downTo 0){
//               val listChecked = _foryou
//               if ()
//           }
//
//
//    }

//    fun removeCheckedProduct() {
//        for (index in _foryou.size downTo 0) {
//            val item = _foryou.getOrNull(index) ?: break
//            if (item.isCheck)
//                _foryou.removeAt(index)
//        }
//        notifyDataSetChanged()
//    }
}