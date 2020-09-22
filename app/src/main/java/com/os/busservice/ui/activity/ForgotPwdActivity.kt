package com.os.busservice.ui.activity

import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.ForgotPwdActivityBinding
import com.os.busservice.model.CommonResponse
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.viewModel.LoginViewModel
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.UtilityMethods
import kotlinx.android.synthetic.main.forgot_pwd_activity.*
import kotlinx.android.synthetic.main.main_toolbar_layout.*


class ForgotPwdActivity : BaseBindingActivity(), View.OnClickListener {
    private var mBinding: ForgotPwdActivityBinding? = null
    var mViewModel: LoginViewModel? = null

    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.forgot_pwd_activity)
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        mBinding?.mViewModel = mViewModel
    }

    override fun createActivityObject() {
    }

    override fun initializeObject() {
        mActivity = this

        toolbarName.text = getString(R.string.forgot_password)

        mViewModel!!.error.observe(this, Observer {
            AppDelegate.showToast(this, it)
        })

        mViewModel!!.commonLiveData.observe(this, Observer {
            forgotPwdApi(it)
        })

    }

    override fun setListeners() {
        signUpTxt.setOnClickListener(this)
        bt_login.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        AppDelegate.hideKeyBoard(this)
        when (p0?.id) {
            R.id.signUpTxt -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            R.id.bt_login -> {
                mViewModel?.forgotPwd()
            }
        }

    }

    private fun forgotPwdApi(result: ApiResponse<CommonResponse>?) {
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

                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    )

                    finish()
                } else
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)


            }


        }

    }

}
