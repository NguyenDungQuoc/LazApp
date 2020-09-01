package com.example.lazapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lazapp.R
import com.example.lazapp.model.FlashSaleProduct
import kotlinx.android.synthetic.main.row_flash_sale.view.*

class   FlashSaleAdapter(
    private val context: Context,
    _product: MutableList<FlashSaleProduct>
) :
    RecyclerView.Adapter<FlashSaleAdapter.ViewHolder>() {
    private var product: MutableList<FlashSaleProduct> = _product

    fun setData(productResult: MutableList<FlashSaleProduct>) {
//        this.product = product
        //product.size = 5
//        val result = mutableListOf<FlashSaleProduct>()
//        for (i in 0..2) {
//            val item = productResult.getOrNull(i)
//            if (item != null)
//                result.add(item)
//        }

        this.product = productResult
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_flash_sale, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val proDuctSale = product.getOrNull(position)
        holder.itemView.apply {
            Glide.with(imgFlashSale.context).load(proDuctSale?.itemImg)
                .into(imgFlashSale)
            tvSoldOutFlashSale.text = "${proDuctSale?.almostSoldOut} Đã bán"
            tvPriceFlashSale.text = "${proDuctSale?.itemPrice} đ"
            tvDiscountFlashSale.text = proDuctSale?.itemDiscount
        }

    }

    override fun getItemCount() = product.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}