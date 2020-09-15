package com.os.busservice.ui.activity

import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.AddressListActivityBinding
import com.os.busservice.listeners.AddressListener
import com.os.busservice.model.CommonResponse
import com.os.busservice.model.address.AddressResponse
import com.os.busservice.model.address.AddressResult
import com.os.busservice.ui.adapter.AddressAdapter
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.Tags
import com.os.busservice.utility.Tags.DATA
import com.os.busservice.utility.UtilityMethods
import kotlinx.android.synthetic.main.address_list_activity.*
import kotlinx.android.synthetic.main.common_toolbar.*

import java.util.ArrayList

class AddressListActivity : BaseBindingActivity(), AddressListener {
    private var mBinding: AddressListActivityBinding?=null

    private var mAddressList=ArrayList<AddressResult>()
    var mAddressAdapter: AddressAdapter? = null
    private var mDefaultAddress: AddressResult? = null

    override fun setBinding() {
        mBinding=DataBindingUtil.setContentView(this, R.layout.address_list_activity)
    }

    override fun createActivityObject() {
        mAddressAdapter = AddressAdapter(this)
//        mViewMode = ViewModelProvider(this).get(CartViewModel::class.java)


     /*   mViewMode?.getAddressLiveData?.observe(this, Observer {
            getAddressApiResponse(it)
        })

        mViewMode?.addAddressLiveData?.observe(this, Observer {
            setDefaultAddressApiResponse(it)
        })*/

    }

    override fun initializeObject() {
        mActivity = this
        mBinding?.mAddressAdapter =mAddressAdapter
    }

    override fun setListeners() {
        toolbarBackBt.setOnClickListener { finish() }
        toolbarName.text = getString(R.string.address_list)
        buyNowBt.setOnClickListener { startActivity(Intent(this,AddressActivity::class.java)) }
        add.visibility=View.VISIBLE
        add.setOnClickListener { startActivity(Intent(this,AddressActivity::class.java)) }
    }

    override fun onStart() {
        super.onStart()
//        mViewMode?.mGetAddress(sessionManager?.loginResult?.id)
    }

    private fun getAddressApiResponse(result: ApiResponse<AddressResponse>?) {
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
                if (result.data?.success!!) {
//                    buyNowBt.text = getString(R.string.change)
                    mAddressList= result.data.result as ArrayList<AddressResult>
                    mAddressAdapter?.mUpdateList(result.data.result)
                    mAddressAdapter?.notifyDataSetChanged()

                    for (item in result.data.result) {
                        if (item.is_default.equals(getString(R.string.yes), true))
                            mDefaultAddress = item
                    }

                } else
                    buyNowBt.text = getString(R.string.add_address)
            }
        }
    }

    override fun mOnItemClick(item: AddressResult?) {
        mDefaultAddress = item
//        mViewMode?.mSetDefaultAddress(item?._id, sessionManager?.loginResult?.id)

    }

    override fun mOnAddressSelect(item: AddressResult?) {
        val intent =Intent()
        intent.putExtra(DATA,item)
        setResult(Tags.mResultCode,intent)
        finish()


    }


    private fun setDefaultAddressApiResponse(result: ApiResponse<CommonResponse>?) {
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
                UtilityMethods.showToastMessage(mActivity!!, result.data?.msg)

                if(result.data?.success!!) {
//                    mViewMode?.mGetAddress(sessionManager?.loginResult?.id)

                    val intent = Intent()
                    intent.putExtra(Tags.DATA, mDefaultAddress)
                    setResult(Tags.mResultCode, intent)
                    finish()
                }

            }

        }
    }
}