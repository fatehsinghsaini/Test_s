package com.os.busservice.ui.activity

import androidx.databinding.DataBindingUtil
import com.os.busservice.R
import com.os.busservice.databinding.SortFilterActivityBinding
import com.os.busservice.ui.baseFile.BaseBindingActivity

class SortAndFilterActivity : BaseBindingActivity() {
    var mBinding:SortFilterActivityBinding?=null
    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.sort_filter_activity)
    }

    override fun createActivityObject() {
    }

    override fun initializeObject() {
    }

    override fun setListeners() {
    }
}