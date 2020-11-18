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
import com.os.busservice.model.busListResponse.BusType
import com.os.busservice.model.busListResponse.SeatListRequest
import com.os.busservice.model.busListResponse.SeatListResponse
import com.os.busservice.model.loginResponse.LoginResponse
import com.os.busservice.ui.adapter.BottomBusTypeDialogAdapter
import com.os.busservice.ui.adapter.BusSearchItemAdapter
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.viewModel.HomeViewModel
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.Tags
import com.os.busservice.utility.UtilityMethods
import kotlinx.android.synthetic.main.bottom_bus_type_dialog.*
import kotlinx.android.synthetic.main.main_toolbar_layout.*

class BusSearchListing :BaseBindingActivity(), BusSearchListener {
    private lateinit var mAdapter: BusSearchItemAdapter
    private lateinit var mBottomDialogAdapter: BottomBusTypeDialogAdapter
    private var mBinding: BusSearchListingBinding?=null
    private var mViewModel:HomeViewModel?=null

    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.bus_search_listing)
    }

    override fun createActivityObject() {
        mAdapter = BusSearchItemAdapter(this)

        val mList = ArrayList<BusType>()
        mList.add(BusType(R.drawable.ic_air_conditioner,getString(R.string.ac),""))
        mList.add(BusType(R.drawable.ic_non_ac,getString(R.string.non_ac),""))
        mList.add(BusType(R.drawable.ic_noun_bus_seat,getString(R.string.seater),""))
        mList.add(BusType(R.drawable.ic_noun_sleeping,getString(R.string.sleeper),""))

        mBottomDialogAdapter = BottomBusTypeDialogAdapter(this,mList,this)
        mViewModel=ViewModelProvider(this).get(HomeViewModel::class.java)


    }

    override fun initializeObject() {
        mActivity=this
        mBinding?.mAdapter = mAdapter
        mBinding?.mBottomDialogAdapter = mBottomDialogAdapter

        var mBusModel: SeatListRequest? = null
        if(intent.hasExtra(Tags.DATA))
            mBusModel =intent.getParcelableExtra(Tags.DATA)

        toolbarName.text ="${mBusModel?.start_point} to ${mBusModel?.end_point}"
        mBinding?.date?.text =mBusModel?.start_date

        mBusModel?.let { mViewModel?.mBusSeatListResponse(it) }

        mViewModel?.mBusSeatLiveData?.observe(this, Observer {
            handleBusListApi(it)
        })

    }

    override fun setListeners() {
        back.setOnClickListener { finish() }
        tvFilter.setOnClickListener {  startActivity(Intent(mActivity,SortAndFilterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)) }
    }


    override fun mSeatSelected(item: BusRouteData?) {
        startActivity(Intent(mActivity,BusSeatActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT).putExtra(Tags.DATA,item))
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