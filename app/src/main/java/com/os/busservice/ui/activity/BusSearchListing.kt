package com.os.busservice.ui.activity

import androidx.databinding.DataBindingUtil
import com.os.busservice.R
import com.os.busservice.databinding.BusSearchListingBinding
import com.os.busservice.ui.baseFile.BaseBindingActivity

class BusSearchListing :BaseBindingActivity() {

    private var mBinding: BusSearchListingBinding?=null

    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.bus_search_listing)
    }

    override fun createActivityObject() {
    }

    override fun initializeObject() {
    }

    override fun setListeners() {
    }
}