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
import com.example.lazapp.model.TrendingProduct
import kotlinx.android.synthetic.main.trending_view.view.*

class TrendingAdapter(
    private val context: Context,
    private val _trending: MutableList<TrendingProduct>
) : RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {
    private var trending: MutableList<TrendingProduct> = _trending

    fun setData(productResult: MutableList<TrendingProduct>) {
//        this.product = product
        //product.size = 5
//        val result = mutableListOf<TrendingProduct>()
//        for (i in 0..2) {
//            val item = productResult.getOrNull(i)
//            if (item != null)
//                result.add(item)
//        }

        this.trending = productResult
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.trending_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trendingPos = trending.getOrNull(position)
        holder.apply {
            Glide.with(imgTrending.context).load(trendingPos?.items?.firstOrNull()?.itemImg)
                .into(imgTrending)
            textTrendingLine1.text = "Top Laz"
            textTrendingLine2.text = "Top Đề Xuất"
        }

    }

    override fun getItemCount() = trending.size
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgTrending: ImageView = itemView.imgTrending
        val textTrendingLine1: TextView = itemView.tv_trending_line1
        val textTrendingLine2: TextView = itemView.tv_trending_line2
    }
}