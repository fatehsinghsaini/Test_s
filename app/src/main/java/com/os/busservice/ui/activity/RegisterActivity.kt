package com.os.busservice.ui.activity

import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.os.busservice.R
import com.os.busservice.databinding.ActivityRegisterBinding
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.viewModel.LoginViewModel
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.Tags
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fb_google_layout.*
import kotlinx.android.synthetic.main.main_toolbar_layout.*

class RegisterActivity : BaseBindingActivity(), View.OnClickListener {
    var binding: ActivityRegisterBinding? = null
    var mViewModel: LoginViewModel? = null
    var userItems: LoginResult? = null

    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding?.mLoginViewModel = mViewModel
        binding?.lifecycleOwner = this
    }

    override fun createActivityObject() {

    }

    override fun initializeObject() {
        mActivity = this

        toolbarName.text = getString(R.string.sign_up)


        if (intent.hasExtra(Tags.DATA)) {
            mViewModel?.pwdShowingFlag!!.value = false
            userItems = intent.getParcelableExtra(Tags.DATA)
            binding?.showPassword = false

          /*  mViewModel!!.firstName.value = userItems?.first_name
            mViewModel!!.lastName.value = userItems?.last_name
            mViewModel!!.mobileNo.value = userItems?.mobile
            mViewModel!!.emial.value = userItems?.email*/

            if(intent.hasExtra(Tags.LOGIN))
                socialModel =intent.getParcelableExtra(Tags.LOGIN)


        } else {
            binding?.showPassword = true
            mViewModel?.pwdShowingFlag!!.value = true
        }


        mViewModel?.error?.observe(this, Observer {
            AppDelegate.showToast(this, it)
        })

   /*     mViewModel!!.otpLiveData.observe(this, Observer {
            handleGenerateOtpApi(it)
        })

        mViewModel!!.addResponseLiveData.observe(this, Observer {
            handleLoginApi(it)
        })*/


    }

    override fun setListeners() {
        back.setOnClickListener(this)
        bt_signup.setOnClickListener(this)
        loginInTxt.setOnClickListener(this)
        fbLayout.setOnClickListener(this)
        googleLayout.setOnClickListener(this)
        privacyPolicy.setOnClickListener(this)
        termsCondition.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        AppDelegate.hideKeyBoard(this)
        when (p0?.id) {
            R.id.loginInTxt -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            R.id.bt_signup -> {
                mViewModel!!.countryCode.value = ccp.selectedCountryCode
                mViewModel!!.mSendOtp(Tags.REGISTER)

                startActivity(Intent(this, OtpVerifyActivity::class.java))
                finish()
            }
            R.id.fbLayout -> faceBookLogin(Tags.REGISTER)
          /*  R.id.googleLayout -> googlePlusLogin(Tags.REGISTER)*/
            R.id.privacyPolicy -> mCallStaticPage(
                getString(R.string.privacy_policy),
                Tags.PRIVACY_POLICY
            )
            R.id.termsCondition -> mCallStaticPage(
                getString(R.string.terms_of_use),
                Tags.TERMS_AND_CONDITION
            )
            R.id.back -> finish()
        }
    }

    private fun mCallStaticPage(mTitle: String?, slag: String?) {
        startActivity(
            Intent(
                mActivity,
                StaticPageActivity::class.java
            ).putExtra(Tags.TITLE, mTitle).putExtra(Tags.DATA, slag)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        )
    }

 /*   private fun handleGenerateOtpApi(result: ApiResponse<LoginResponse>?) {
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
                    val userItem = result.data.result

                    userItem.first_name = mViewModel?.firstName?.value
                    userItem.last_name = mViewModel?.lastName?.value
                    userItem.email = mViewModel?.emial?.value
                    userItem.mobile = mViewModel?.mobileNo?.value
                    userItem.country_code = mViewModel?.countryCode?.value
                    userItem.pwd = mViewModel?.password?.value

                    if (socialModel != null) {
                        if (socialModel?.loginType == Tags.facebook)
                            userItem.fb_id = socialModel?.socialId
                        else
                            userItem.google_id = socialModel?.socialId
                    }

                    startActivity(
                        Intent(
                            this,
                            OtpVerifyActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra(
                                Tags.DATA,
                                userItem
                            )
                    )

                    finish()
                } else
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)
            }
        }
    }*/


    override fun onDestroy() {
        super.onDestroy()
        LoginManager.getInstance().logOut()
    }

   /* private fun handleLoginApi(result: ApiResponse<LoginResponse>?) {
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
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)
                    val userItem = result.data.result
                    sessionManager?.loginResult=userItem
                    sessionManager?.isLogin=true
                    startActivity(
                        Intent(
                            this,
                            DashBoardActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                    finish()
                } else
                {
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)
                }
            }
        }
    }*/


}