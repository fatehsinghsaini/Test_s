package com.os.busservice.ui.adapter

import androidx.databinding.ViewDataBinding
import com.os.busservice.R
import com.os.busservice.databinding.AddressItemBinding
import com.os.busservice.databinding.RecentBookingItemBinding
import com.os.busservice.listeners.AddressListener
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult


class RecentBookingAdapter(var mCartListener: CommonListener) : RecyclerBaseAdapter() {

    var mList = ArrayList<String>()
    var mPos = 0

    override fun getLayoutIdForPosition(position: Int) = R.layout.recent_booking_item

    override fun putViewDataBinding(binding: ViewDataBinding) {
        if (binding is RecentBookingItemBinding) {

            val item = mList[mPos]
//            binding.mItem = item
           /* binding.defaultCheckBox.isChecked = item.is_default == "yes"

            binding.defaultCheckBox.setOnClickListener {
                if (item.is_default != "yes")
                    mCartListener.mOnItemClick(item)
                else
                    binding.defaultCheckBox.isChecked=true
            }*/
            binding.bookNow.setOnClickListener {

            }
        }

    }

    override fun getViewModel(position: Int) {
        mList[position]
        mPos = position
    }

    override fun getItemCount(): Int = mList.size

    fun mUpdateList(list: ArrayList<String>) {
        mList = list
    }
}