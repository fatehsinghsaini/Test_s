package com.os.busservice.ui.activity
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.login.LoginManager
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.OfferListActivityBinding
import com.os.busservice.model.CommonResponse
import com.os.busservice.model.couponCode.CouponData
import com.os.busservice.model.couponCode.CouponListResponse
import com.os.busservice.ui.adapter.OfferAdapter
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.viewModel.MoreViewModel
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.UtilityMethods
import kotlinx.android.synthetic.main.main_toolbar_layout.*
import kotlinx.android.synthetic.main.offer_list_activity.*


class OfferListingActivity : BaseBindingActivity()  {
    lateinit var  mAdapter: OfferAdapter
    var mBinding:OfferListActivityBinding?=null
    lateinit var mViewMode:MoreViewModel
    override fun setBinding() {
     setContentView(R.layout.offer_list_activity)
       mBinding = DataBindingUtil.setContentView(this,R.layout.offer_list_activity)
        mBinding?.mAdapter = mAdapter
    }

    override fun createActivityObject() {
        mActivity=this
        mAdapter = OfferAdapter()
        mViewMode = ViewModelProvider(this).get(MoreViewModel::class.java)
        mViewMode.mGetCouponList(sessionManager?.loginResult?.user_id)

    }

    override fun initializeObject() {
        toolbarName.text = getString(R.string.offers)
        mViewMode.mCouponListLiveData.observe(this, Observer {
            mHandleCouponListApi(it)
        })
    }

    override fun setListeners() {
        back.setOnClickListener { finish() }
    }

    private fun mHandleCouponListApi(result: ApiResponse<CouponListResponse>?) {
        when (result!!.status) {
            ApiResponse.Status.ERROR -> {
                ProgressDialog.hideProgressDialog()
                UtilityMethods.showToastMessage(this, getString(R.string.error_network_connection))
            }
            ApiResponse.Status.LOADING -> ProgressDialog.showProgressDialog(this)
            ApiResponse.Status.SUCCESS -> {
                ProgressDialog.hideProgressDialog()
                LoginManager.getInstance().logOut()

                if (result.data?.status!!) {

                    noDataFound.visibility = View.GONE
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)
                    mAdapter.mUpdateList(result.data.data as ArrayList<CouponData>)
                    mAdapter.notifyDataSetChanged()



                } else
                {
                    noDataFound.visibility = View.VISIBLE
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)
                }
            }
        }
    }


}