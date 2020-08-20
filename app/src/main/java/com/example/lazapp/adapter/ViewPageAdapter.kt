package com.example.lazapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.lazapp.model.PromotionProduct
import com.example.lazapp.PromotionFragment

class ViewPageAdapter(
    fm: FragmentManager,
    private val promotion: MutableList<PromotionProduct>? = null
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var pro: MutableList<PromotionProduct>? = promotion
    override fun getCount(): Int {
        return promotion?.size ?:0
    }

    override fun getItem(position: Int): Fragment {
        return PromotionFragment.newInstance(promotion?.getOrNull(position))
    }
    fun setData(promo: MutableList<PromotionProduct>) {
        this.pro = promo
        notifyDataSetChanged()
    }

}
