package com.os.busservice.ui.activity

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.ActivityVerifyOtpBinding
import com.os.busservice.model.loginResponse.LoginResponse
import com.os.busservice.model.loginResponse.LoginResult
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.viewModel.LoginViewModel
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.Tags
import com.os.busservice.utility.UtilityMethods

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_verify_otp.*
import kotlinx.android.synthetic.main.main_toolbar_layout.*
import java.util.concurrent.TimeUnit

class OtpVerifyActivity : BaseBindingActivity(), View.OnClickListener {

    var binding: ActivityVerifyOtpBinding? = null
    private var oTP: String = ""
    private val wait = 60
    private var timer: Disposable? = null
    private var mViewModel: LoginViewModel? = null
    private var userItems: LoginResult? = null

    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_otp)
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun createActivityObject() {
    }

    private fun autoOtpFill(otp: String) {
        otp_tv_otp_1.setText(otp)
    }

    override fun initializeObject() {
        mActivity = this
        toolbarName.text = getString(R.string.verification)
        txt_resend.isEnabled = false
        txt_label3.visibility =View.GONE
        setTimerForOTP()

        if (intent.hasExtra(Tags.DATA)) {
            userItems = intent.getParcelableExtra(Tags.DATA)
            oTP = userItems?.otp!!
            autoOtpFill(oTP)
        }

        mViewModel?.mobileNo?.value = userItems?.mobile_number
        mViewModel?.countryCode?.value = userItems?.country_code

        mViewModel?.error?.observe(this, Observer {
            UtilityMethods.showToastMessage(mActivity!!, it)
        })
        mViewModel!!.loginLiveData.observe(this, Observer {
            apiResponse(it, Tags.REGISTER)
        })
        mViewModel!!.otpLiveData.observe(this, Observer {
            apiResponse(it, "")
        })

    }

    override fun setListeners() {
        txt_resend.setOnClickListener(this)
        btn_verifyotp.setOnClickListener(this)
        back.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_verifyotp -> {

                if (otp_tv_otp_1.text.toString()
                        .isNotEmpty() && otp_tv_otp_1.text.toString().length == 6
                ) {
                    val getOtp = otp_tv_otp_1.text.toString()
                    if (getOtp == oTP)
                    {
                        mViewModel?.registerApi(sessionManager?.mFCMToken, userItems)
                    }
                    else
                        AppDelegate.showToast(this, getString(R.string.invalid_otp))
                } else
                    AppDelegate.showToast(this, getString(R.string.invalid_otp))
            }
            R.id.txt_resend -> {
                mViewModel?.mSendOtp("")
            }
            R.id.back ->finish()
        }
    }

    private fun apiResponse(
        result: ApiResponse<LoginResponse>?,
        registerFlag: String
    ) {
        when (result!!.status) {
            ApiResponse.Status.ERROR -> {
                ProgressDialog.hideProgressDialog()
                UtilityMethods.showToastMessage(this, getString(R.string.error_network_connection))
            }
            ApiResponse.Status.LOADING -> ProgressDialog.showProgressDialog(this)
            ApiResponse.Status.SUCCESS -> {
                ProgressDialog.hideProgressDialog()
                if (result.data!!.success) {
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)
                    if (registerFlag == Tags.REGISTER) {
                        sessionManager?.loginResult =  result.data.result
                        sessionManager?.isLogin=true
                        startActivity(
                            Intent(
                                this,
                                DashBoardActivity::class.java
                            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        )
                        finish()
                    } else {
                        userItems = result.data.result
                        UtilityMethods.showToastMessage(mActivity!!, result.data.msg)
                    }

                } else
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)
            }
        }
    }


    private fun setTimerForOTP() {
        timer = Observable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ result ->
                val remainingTime = wait - result
                val resendTxt =
                    getString(R.string.resend_code) + " " + remainingTime.toString() + " " + getString(
                        R.string.sec
                    )
                txt_resend.text = resendTxt
                if (result >= wait) {
                    timer!!.dispose()
                    txt_resend.text = getString(R.string.resend_code)
                    txt_resend.isEnabled = true
                    txt_label3.visibility =View.VISIBLE
                }
            },
                { error -> Log.e("TAG", "{$error.message}") },
                { Log.d("TAG", "completed") })
    }


    override fun onDestroy() {
        super.onDestroy()
        if (timer != null)
            timer!!.dispose()
    }

}