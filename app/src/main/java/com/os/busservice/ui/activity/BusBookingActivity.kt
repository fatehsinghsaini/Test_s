package com.os.busservice.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.BusBookingActivityBinding
import com.os.busservice.databinding.BusSearchListingBinding
import com.os.busservice.listeners.BusSearchListener
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.PaytmResponse
import com.os.busservice.model.RequestModel
import com.os.busservice.model.address.AddressResult
import com.os.busservice.model.busListResponse.BusRouteData
import com.os.busservice.model.couponCode.CouponListResponse
import com.os.busservice.ui.adapter.BusSearchItemAdapter
import com.os.busservice.ui.adapter.PassengerInfoAdapter
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.viewModel.BookingViewModel
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.UtilityMethods
import com.paytm.pgsdk.PaytmOrder
import com.paytm.pgsdk.PaytmPaymentTransactionCallback
import com.paytm.pgsdk.TransactionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bus_booking_activity.*
import kotlinx.android.synthetic.main.main_toolbar_layout.*
import java.security.SecureRandom
import java.util.*

class BusBookingActivity :BaseBindingActivity(), BusSearchListener {

    private lateinit var mAdapter: PassengerInfoAdapter
    private var mBinding: BusBookingActivityBinding?=null
    private var mViewModel:BookingViewModel?=null
    private var orderId:String?=null

    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.bus_booking_activity)
    }

    override fun createActivityObject() {
        mAdapter = PassengerInfoAdapter(this)
        mViewModel= ViewModelProvider(this).get(BookingViewModel::class.java)

        mViewModel?.mPaytmChecksumLiveData?.observe(this, Observer {
            mHandlePaytmChecksum(it)
        })

    }

    override fun initializeObject() {
        mActivity=this
        mBinding?.mAdapter = mAdapter
        toolbarName.text ="Jaipur to Kota"

    }

    override fun setListeners() {
        back.setOnClickListener { finish() }
        priceSubmit.setOnClickListener {

        }
    }

    override fun mSeatSelected(item: BusRouteData?) {
    }

    override fun mBusTrackingClick(item: BusRouteData?) {
    }

    fun mPayingOnline(mTotalItemPrice: String?) {
        //create random coupon code

        val mOrderId = "122854411"

        val mRequest = RequestModel()
        mRequest.amount = "1"
        mRequest.order_id=mOrderId
        mRequest.language="en"
        mRequest.cust_id="c11l"
        mRequest.cust_mobile=sessionManager?.loginResult?.mobile_number
        mRequest.cust_email=sessionManager?.loginResult?.email

        mViewModel?.mGeneratePaytmChecksum(mRequest)

    }


     private fun mHandlePaytmChecksum(result: ApiResponse<CouponListResponse>?) {
         when (result!!.status) {
             ApiResponse.Status.ERROR -> {
                 ProgressDialog.hideProgressDialog()
                 UtilityMethods.showToastMessage(this, getString(R.string.error_network_connection))
             }
             ApiResponse.Status.LOADING -> ProgressDialog.showProgressDialog(this)
             ApiResponse.Status.SUCCESS -> {
                 ProgressDialog.hideProgressDialog()
                 val response = result.data
                 UtilityMethods.showToastMessage(mActivity!!, response?.msg)

                 if (result.data?.status!!) {

                     val checksum = "lajsdljaasdsdf"

                     var host = "https://securegw-stage.paytm.in/"
                     if (true) {
                         host = "https://securegw.paytm.in/"
                     }

                     val callBackUrl =
                         host + "theia/paytmCallback?ORDER_ID=" + orderId

                     val paytmOrder = PaytmOrder(
                         orderId,
                         "WvYwMF41524640051345",
                         checksum,
                         "1",
                         callBackUrl
                     )
                     val transactionManager = TransactionManager(paytmOrder, object  :
                         PaytmPaymentTransactionCallback {
                         override fun onTransactionResponse(p0: Bundle?) {

                         }

                         override fun clientAuthenticationFailed(p0: String?) {

                         }

                         override fun someUIErrorOccurred(p0: String?) {

                         }

                         override fun onTransactionCancel(p0: String?, p1: Bundle?) {

                         }

                         override fun networkNotAvailable() {

                         }

                         override fun onErrorProceed(p0: String?) {

                         }

                         override fun onErrorLoadingWebPage(p0: Int, p1: String?, p2: String?) {

                         }

                         override fun onBackPressedCancelTransaction() {

                         }

                     })
                     transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage")
                     transactionManager.startTransaction(this, 101)

                 }


             }
         }
     }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && data != null) {
            val response= data.getStringExtra("response")

            Log.d("onPaymentSuccess=",response!!)

            if(response.isNotEmpty()){

                val gson = Gson()
                val jsonObject= gson.fromJson<PaytmResponse>(response, PaytmResponse::class.java)



            }else
                Toast.makeText(this,getString(R.string.tx_cancel), Toast.LENGTH_SHORT).show()

        }
    }

}