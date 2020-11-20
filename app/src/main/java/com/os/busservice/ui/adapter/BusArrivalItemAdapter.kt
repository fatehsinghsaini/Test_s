package com.os.busservice.ui.adapter

import androidx.databinding.ViewDataBinding
import com.os.busservice.R
import com.os.busservice.databinding.*
import com.os.busservice.listeners.AddressListener
import com.os.busservice.listeners.CommonListener
import com.os.busservice.listeners.SafetyListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.ui.adapter.RecyclerBaseAdapter


class BusArrivalItemAdapter() : RecyclerBaseAdapter() {

    var mList = ArrayList<String>()
    var mPos = 0

    override fun getLayoutIdForPosition(position: Int) = R.layout.bus_arrival_item

    override fun putViewDataBinding(binding: ViewDataBinding) {
        if (binding is BusArrivalItemBinding) {

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