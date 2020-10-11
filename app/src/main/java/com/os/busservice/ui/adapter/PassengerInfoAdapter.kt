package com.os.busservice.ui.adapter
import androidx.databinding.ViewDataBinding
import com.os.busservice.R
import com.os.busservice.databinding.AddressItemBinding
import com.os.busservice.databinding.BusSearchItemBinding
import com.os.busservice.databinding.PassengerInfoItemsBinding
import com.os.busservice.databinding.RecentBookingItemBinding
import com.os.busservice.listeners.AddressListener
import com.os.busservice.listeners.BusSearchListener
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult
class PassengerInfoAdapter(var mCartListener: BusSearchListener) : RecyclerBaseAdapter() {
    var mList = ArrayList<String>()
    var mPos = 0

    override fun getLayoutIdForPosition(position: Int) = R.layout.passenger_info_items

    override fun putViewDataBinding(binding: ViewDataBinding) {
        if (binding is PassengerInfoItemsBinding) {
            binding.mListener =mCartListener

        }

    }

    override fun getViewModel(position: Int) {
        /*mList[position]
        mPos = position*/
    }

    override fun getItemCount(): Int = 3

    fun mUpdateList(list: ArrayList<String>) {
        mList = list
    }
}