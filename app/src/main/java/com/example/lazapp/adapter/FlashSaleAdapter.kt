package com.example.lazapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lazapp.R
import com.example.lazapp.model.FlashSaleProduct
import kotlinx.android.synthetic.main.flash_sale_view.view.*

class FlashSaleAdapter(
    private val context: Context,
    private var _product: MutableList<FlashSaleProduct>
) :
    RecyclerView.Adapter<FlashSaleAdapter.ViewHolder>() {
    private var product: MutableList<FlashSaleProduct> = _product

    fun setData(productResult: MutableList<FlashSaleProduct>) {
//        this.product = product
        //product.size = 5
        val result = mutableListOf<FlashSaleProduct>()
        for (i in 0..2) {
            val item = productResult.getOrNull(i)
            if (item != null)
                result.add(item)
        }

        this.product = result
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.flash_sale_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val proDuctSale = product.getOrNull(position)
        Glide.with(holder.imgFlashSaleProduct.context).load(proDuctSale?.itemImg)
            .into(holder.imgFlashSaleProduct)
        holder.textSoldOut.text = "${proDuctSale?.almostSoldOut} Đã bán"
        holder.texPrice.text = "${proDuctSale?.itemPrice} đ"
    }

    override fun getItemCount() = product.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgFlashSaleProduct: ImageView = itemView.imgFlashSale
        val textSoldOut: TextView = itemView.tvSoldOutFlashSale
        val texPrice: TextView = itemView.tvPriceFlashSale
    }
}