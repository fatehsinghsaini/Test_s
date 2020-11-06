package com.os.busservice.ui.adapter

import androidx.databinding.ViewDataBinding
import com.os.busservice.R
import com.os.busservice.databinding.*
import com.os.busservice.listeners.AddressListener
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.model.couponCode.CouponData
import com.os.busservice.ui.adapter.RecyclerBaseAdapter


class OfferAdapter : RecyclerBaseAdapter() {

    var mList = ArrayList<CouponData>()
    var mPos = 0

    override fun getLayoutIdForPosition(position: Int) = R.layout.offer_item

    override fun putViewDataBinding(binding: ViewDataBinding) {
        if (binding is OfferItemBinding) {
            binding.mItem = mList[mPos]
        }

    }

    override fun getViewModel(position: Int) {
        mList[position]
        mPos = position
    }

    override fun getItemCount(): Int = mList.size

    fun mUpdateList(list: ArrayList<CouponData>) {
        mList = list
    }
}