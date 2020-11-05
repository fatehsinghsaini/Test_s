package com.os.busservice.ui.activity

import androidx.databinding.DataBindingUtil
import com.os.busservice.R
import com.os.busservice.databinding.OfferListActivityBinding
import com.os.busservice.ui.adapter.OfferAdapter
import com.os.busservice.ui.baseFile.BaseBindingActivity

import kotlinx.android.synthetic.main.common_toolbar.*


class OfferListingActivity : BaseBindingActivity()  {
    lateinit var  mAdapter: OfferAdapter
    var mBinding:OfferListActivityBinding?=null
    override fun setBinding() {
     setContentView(R.layout.offer_list_activity)
       mBinding = DataBindingUtil.setContentView(this,R.layout.offer_list_activity)
        mBinding?.mAdapter = mAdapter

    }

    override fun createActivityObject() {
        mActivity=this
        mAdapter = OfferAdapter()
    }

    override fun initializeObject() {
        toolbarName.text = getString(R.string.offers)

    }

    override fun setListeners() {
        toolbarBackBt.setOnClickListener { finish() }



    }

}