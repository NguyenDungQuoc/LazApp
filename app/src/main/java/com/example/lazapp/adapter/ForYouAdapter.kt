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
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_foryou.view.*

class ForYouAdapter(
    private val context: Context,
    private var _foryou: MutableList<ForYouProduct>
) :
    RecyclerView.Adapter<ForYouAdapter.ViewHolder>() {
    private var productForYou: MutableList<ForYouProduct> = _foryou
    var onClick: ((ForYouProduct?) -> (Unit))? = null

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
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_foryou, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productFY = productForYou.getOrNull(position)
        holder.itemView.apply {
            Glide.with(imgForYou.context).load(productFY?.itemImg)
                .into(imgForYou)
            tvNameForYou.text = productFY?.itemTitle
            tvDiscountForYou.text = productFY?.itemDiscount
            tvPriceDiscountForYou.text = "${productFY?.itemDiscountPrice} đ"
            if (productFY?.itemPrice != "") {
                tvPriceForYou.text = "${productFY?.itemPrice} đ"
                tvPriceForYou.paintFlags =
                    tvPriceForYou.paintFlags or TextPaint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvPriceForYou.text = ""
            }
            ratingBarForYou.rating = (productFY?.itemRatingScore ?: 0) as Float

        }
    }

    override fun getItemCount() = productForYou.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val imgForYou: ImageView = itemView.imgForYou
//        val textDiscount: TextView = itemView.tvDiscountForYou
//        val textNameForYou: TextView = itemView.tvNameForYou
//        val texPriceForYou:TextView = itemView.tvPriceForYou
//        val textPriceDiscoutForYou:TextView = itemView.tvPriceDiscountForYou
        init {
            itemView.setOnClickListener {
                var position = adapterPosition
                var item = productForYou[position]
                onClick?.invoke(item)
            }
        }
    }
}