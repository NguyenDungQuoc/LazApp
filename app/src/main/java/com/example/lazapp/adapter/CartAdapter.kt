package com.example.lazapp.adapter

import android.content.Context
import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.os.Build
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lazapp.R
import com.example.lazapp.model.ForYouProduct
import kotlinx.android.synthetic.main.row_cart.view.*

class CartAdapter(
    private val context: Context,
    private var _foryou: MutableList<ForYouProduct>
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var productForYou: MutableList<ForYouProduct> = _foryou
    private val listCheck: MutableList<ForYouProduct> = mutableListOf()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {

            itemView.checkBox.setOnClickListener {
                val position = adapterPosition
                val itemCB = productForYou.getOrNull(position)
                itemCB?.isCheck = true
            }

            itemView.btnAdd.setOnClickListener {
               val position = adapterPosition
               val item = productForYou.getOrNull(position)
                item?.numberProductCart = item?.numberProductCart?.plus(1)
                itemView.textNumber.text = item?.numberProductCart.toString()
            }
            itemView.btnReduce.setOnClickListener {
                val position = adapterPosition
                val item = productForYou.getOrNull(position)
                if ((item?.numberProductCart ?: 2) > 1) {
                    item?.numberProductCart = item?.numberProductCart?.minus(1)
                    itemView.textNumber.text = item?.numberProductCart.toString()
                }

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
            textNumber.text = productFY?.numberProductCart.toString()
//            if(productFY?.isCheck == true){
//                checkBox.isChecked = true
//            }else {
//                checkBox.isChecked = false
//            }
            checkBox.isChecked = productFY?.isCheck == true
        }


    }

    override fun getItemCount() = productForYou.size

    @RequiresApi(Build.VERSION_CODES.N)
    fun getSumPrice() : String {
        var sum = 0
        for (i in productForYou)
        {
            val price =  (i.itemDiscountPrice?.replace(".","")?.toIntOrNull() ?:0) * (i.numberProductCart ?:0)
            sum += price
        }
        val format: NumberFormat = DecimalFormat("#,###")
        return format.format(sum)
    }

    fun getListCheckedProduct(): MutableList<ForYouProduct> {

        for (i in 0 until _foryou.size) {
            val item = _foryou[i]
            if (item.isCheck)
                listCheck.add(item)
        }
        return listCheck
    }

    fun removeCheckedProduct() {
        for (index in (_foryou.size - 1) downTo 0) {
            val item = _foryou.getOrNull(index) ?: break
            if (item.isCheck)
                _foryou.removeAt(index)
        }
        notifyDataSetChanged()
    }

}