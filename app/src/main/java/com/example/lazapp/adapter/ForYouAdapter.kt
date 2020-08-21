package com.example.lazapp.adapter

import android.content.Context
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lazapp.R
import com.example.lazapp.model.FlashSaleProduct
import com.example.lazapp.model.ForYouProduct
import com.example.lazapp.model.TrendingProduct
import kotlinx.android.synthetic.main.flash_sale_view.view.*
import kotlinx.android.synthetic.main.foryou_view.view.*

class ForYouAdapter(
    private val context: Context,
    private var _foryou: MutableList<ForYouProduct>
) :
    RecyclerView.Adapter<ForYouAdapter.ViewHolder>() {
    private var productForYou : MutableList<ForYouProduct> = _foryou

    fun setData(productResult: MutableList<ForYouProduct>) {
////        this.product = product
//        //product.size = 5
//        val result = mutableListOf<FlashSaleProduct>()
//        for (i in 0..2) {
//            val item = productResult.getOrNull(i)
//            if (item != null)
//                result.add(item)
//        }

        this.productForYou = productResult
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.foryou_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val productFY = productForYou.getOrNull(position)
        holder.itemView.apply {
            Glide.with(imgForYou.context).load(productFY?.itemImg)
                .into(imgForYou)
            tvNameForYou.text = productFY?.itemTitle
            tvDiscountForYou.text = productFY?.itemDiscount
            tvPriceDiscountForYou.text = "${productFY?.itemDiscountPrice} đ"
            if(productFY?.itemPrice != ""){
                tvPriceForYou.text = "${productFY?.itemPrice} đ"
                tvPriceForYou.paintFlags = tvPriceForYou.paintFlags or TextPaint.STRIKE_THRU_TEXT_FLAG
            }else{
                tvPriceForYou.text = ""
            }
        }
    }

    override fun getItemCount() = productForYou.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imgForYou: ImageView = itemView.imgForYou
//        val textDiscount: TextView = itemView.tvDiscountForYou
//        val textNameForYou: TextView = itemView.tvNameForYou
//        val texPriceForYou:TextView = itemView.tvPriceForYou
//        val textPriceDiscoutForYou:TextView = itemView.tvPriceDiscountForYou
    }
}