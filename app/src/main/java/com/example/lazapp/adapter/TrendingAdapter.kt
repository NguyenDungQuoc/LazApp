package com.example.lazapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lazapp.R
import com.example.lazapp.model.TrendingProduct
import kotlinx.android.synthetic.main.row_trending.view.*

class TrendingAdapter(
    private val context: Context,
    _trending: MutableList<TrendingProduct>
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
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_trending, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trendingPos = trending.getOrNull(position)
        holder.itemView.apply {
            Glide.with(imgTrending.context).load(trendingPos?.items?.firstOrNull()?.itemImg)
                .into(imgTrending)
            tvTrendingLine1.text = "Top Laz"
            tvTrendingLine2.text = "Top Đề Xuất"
        }

    }

    override fun getItemCount() = trending.size
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}