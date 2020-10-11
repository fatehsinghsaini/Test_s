package com.os.busservice.ui.activity

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.os.busservice.R
import com.os.busservice.databinding.BusBookingActivityBinding
import com.os.busservice.databinding.BusSearchListingBinding
import com.os.busservice.listeners.BusSearchListener
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.ui.adapter.BusSearchItemAdapter
import com.os.busservice.ui.adapter.PassengerInfoAdapter
import com.os.busservice.ui.baseFile.BaseBindingActivity
import kotlinx.android.synthetic.main.main_toolbar_layout.*

class BusBookingActivity :BaseBindingActivity(), BusSearchListener {

    private lateinit var mAdapter: PassengerInfoAdapter
    private var mBinding: BusBookingActivityBinding?=null

    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.bus_booking_activity)
    }

    override fun createActivityObject() {
        mAdapter = PassengerInfoAdapter(this)

    }

    override fun initializeObject() {
        mActivity=this
        mBinding?.mAdapter = mAdapter
        toolbarName.text ="Jaipur to Kota"

    }

    override fun setListeners() {
        back.setOnClickListener { finish() }
    }

    override fun mSeatSelected(item: AddressResult?) {
    }

    override fun mBusTrackingClick(item: AddressResult?) {
    }
}