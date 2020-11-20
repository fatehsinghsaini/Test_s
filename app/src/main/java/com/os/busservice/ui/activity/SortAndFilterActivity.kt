package com.os.busservice.ui.activity

import androidx.databinding.DataBindingUtil
import com.os.busservice.R
import com.os.busservice.databinding.SortFilterActivityBinding
import com.os.busservice.ui.adapter.BusArrivalItemAdapter
import com.os.busservice.ui.baseFile.BaseBindingActivity
import kotlinx.android.synthetic.main.main_toolbar_layout.*

class SortAndFilterActivity : BaseBindingActivity() {
    var mBinding:SortFilterActivityBinding?=null
    var mAdapter:BusArrivalItemAdapter?=null
    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.sort_filter_activity)
    }

    override fun createActivityObject() {
        mAdapter = BusArrivalItemAdapter()
    }

    override fun initializeObject() {
        mActivity =this

        mBinding?.mArrivalAdapter = mAdapter


    }

    override fun setListeners() {

        back.setOnClickListener { finish() }

    }
}