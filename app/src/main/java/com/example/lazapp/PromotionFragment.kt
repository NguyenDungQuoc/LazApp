package com.example.lazapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.lazapp.model.PromotionProduct
import kotlinx.android.synthetic.main.fragment_promotion.*
import kotlinx.android.synthetic.main.fragment_promotion.view.*

class PromotionFragment : Fragment(R.layout.fragment_promotion) {
    private var promotionProduct: PromotionProduct? = null

    companion object {
        fun newInstance(promotionProduct: PromotionProduct?) = PromotionFragment().apply {
            this.promotionProduct = promotionProduct
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(imgViewPager.context).load(promotionProduct?.imageURL).into(imgViewPager)

    }
}