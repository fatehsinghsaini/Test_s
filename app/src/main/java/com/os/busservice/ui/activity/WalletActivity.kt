package com.os.busservice.ui.activity

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.ActivityMyWalletBinding
import com.os.busservice.model.Transaction
import com.os.busservice.model.WalletHistoryResponse
import com.os.busservice.ui.adapter.WalletAdapter
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.viewModel.MoreViewModel
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.UtilityMethods
import kotlinx.android.synthetic.main.main_toolbar_layout.*

class WalletActivity :BaseBindingActivity() {
    private var mBinding: ActivityMyWalletBinding?=null
    private var mViewModel: MoreViewModel?=null
    private var mAdapter: WalletAdapter?=null

    override fun setBinding() {
        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_my_wallet)

    }

    override fun createActivityObject() {
    }

    override fun initializeObject() {
        mActivity=this
        mViewModel= ViewModelProvider(this).get(MoreViewModel::class.java)
        mAdapter=WalletAdapter()
        mBinding?.mWalletAdapter=mAdapter

        toolbarName.text = getString(R.string.my_wallet)
      /*  mViewModel?.mWalletHistory(sessionManager?.language!!)
        mViewModel?.mWalletLiveData?.observe(this, Observer {
            mGetWalletHistory(it)
        })*/
    }

    private fun mGetWalletHistory(result: ApiResponse<WalletHistoryResponse>?) {
        when (result!!.status) {
            ApiResponse.Status.ERROR -> {
                ProgressDialog.hideProgressDialog()
                UtilityMethods.showToastMessage(
                    mActivity!!,
                    getString(R.string.error_network_connection)
                )
            }
            ApiResponse.Status.LOADING -> ProgressDialog.showProgressDialog(mActivity!!)
            ApiResponse.Status.SUCCESS -> {
                ProgressDialog.hideProgressDialog()
                if (result.data!!.status) {
                    val items = result.data.data
                    mBinding?.mRewardAmount=items.WalletBalance
                    mAdapter?.updateList(items.Transaction as ArrayList<Transaction>)
                    mAdapter?.notifyDataSetChanged()
                } else
                    UtilityMethods.showToastMessage(mActivity!!, result.data.message)
            }
        }

    }

    override fun setListeners() {
        back.setOnClickListener{
            finish()
        }
    }
}