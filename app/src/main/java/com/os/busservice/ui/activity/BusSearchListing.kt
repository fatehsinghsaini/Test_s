package com.os.busservice.ui.activity

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.login.LoginManager
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.BusSearchListingBinding
import com.os.busservice.listeners.BusSearchListener
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.model.busListResponse.BusRouteData
import com.os.busservice.model.busListResponse.SeatListRequest
import com.os.busservice.model.busListResponse.SeatListResponse
import com.os.busservice.model.loginResponse.LoginResponse
import com.os.busservice.ui.adapter.BusSearchItemAdapter
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.viewModel.HomeViewModel
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.Tags
import com.os.busservice.utility.UtilityMethods
import kotlinx.android.synthetic.main.main_toolbar_layout.*

class BusSearchListing :BaseBindingActivity(), BusSearchListener {

    private lateinit var mAdapter: BusSearchItemAdapter
    private var mBinding: BusSearchListingBinding?=null
    private var mViewModel:HomeViewModel?=null

    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.bus_search_listing)
    }

    override fun createActivityObject() {
        mAdapter = BusSearchItemAdapter(this)
        mViewModel=ViewModelProvider(this).get(HomeViewModel::class.java)


    }

    override fun initializeObject() {
        mActivity=this
        mBinding?.mAdapter = mAdapter

        var mBusModel: SeatListRequest? = null
        if(intent.hasExtra(Tags.DATA))
            mBusModel =intent.getParcelableExtra(Tags.DATA)

        toolbarName.text ="${mBusModel?.start_point} to ${mBusModel?.end_point}"

        mBusModel?.let { mViewModel?.mBusSeatListResponse(it) }

        mViewModel?.mBusSeatLiveData?.observe(this, Observer {
            handleBusListApi(it)
        })

    }

    override fun setListeners() {
        back.setOnClickListener { finish() }
    }



    override fun mSeatSelected(item: BusRouteData?) {
        startActivity(Intent(mActivity,BusSeatActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
    }

    override fun mBusTrackingClick(item: BusRouteData?) {
        startActivity(Intent(mActivity,BusTrackingActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
    }

    private fun handleBusListApi(result: ApiResponse<SeatListResponse>?) {
        when (result!!.status) {
            ApiResponse.Status.ERROR -> {
                ProgressDialog.hideProgressDialog()
                UtilityMethods.showToastMessage(this, getString(R.string.error_network_connection))
            }
            ApiResponse.Status.LOADING -> ProgressDialog.showProgressDialog(this)
            ApiResponse.Status.SUCCESS -> {
                ProgressDialog.hideProgressDialog()
                LoginManager.getInstance().logOut()

                if (result.data?.success!!) {
                    UtilityMethods.showToastMessage(mActivity!!, result.data.message)
                    val userItem = result.data.result
                    mAdapter.mUpdateList(userItem as ArrayList<BusRouteData>)
                    mAdapter.notifyDataSetChanged()

                } else
                {
                    UtilityMethods.showToastMessage(mActivity!!, result.data.message)
                }
            }
        }
    }

}