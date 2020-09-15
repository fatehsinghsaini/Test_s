package com.os.busservice.ui.activity

import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.ChangePwdActivityBinding
import com.os.busservice.model.CommonResponse
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.viewModel.LoginViewModel
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.UtilityMethods
import kotlinx.android.synthetic.main.change_pwd_activity.*
import kotlinx.android.synthetic.main.common_toolbar.*


class ChangePwdActivity : BaseBindingActivity(), View.OnClickListener {
    private var mBinding: ChangePwdActivityBinding? = null
    var mViewModel: LoginViewModel? = null

    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.change_pwd_activity)
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        mBinding?.mViewModel = mViewModel
    }

    override fun createActivityObject() {



    }

    override fun initializeObject() {
        mActivity = this

        toolbarName.text=getString(R.string.change_password)

        mViewModel!!.error.observe(this, Observer {
            AppDelegate.showToast(this, it)
        })

        mViewModel!!.commonLiveData.observe(this, Observer {
            changePwdApi(it)
        })

        mViewModel?.logoutLiveData?.observe(this, Observer {
            handleLogoutApi(it)
        })

    }

    override fun setListeners() {
        bt_login.setOnClickListener(this)
        toolbarBackBt.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {


        AppDelegate.hideKeyBoard(this)
        when (p0?.id) {
            R.id.toolbarBackBt -> {
                finish()
            }
            R.id.bt_login -> {
               /* mViewModel?.changePwd(sessionManager?.loginResult?.id)*/
            }
        }

    }

    private fun changePwdApi(result: ApiResponse<CommonResponse>?) {
        when (result!!.status) {
            ApiResponse.Status.ERROR -> {
                ProgressDialog.hideProgressDialog()
                UtilityMethods.showToastMessage(this, getString(R.string.error_network_connection))
            }

            ApiResponse.Status.LOADING -> ProgressDialog.showProgressDialog(this)
            ApiResponse.Status.SUCCESS -> {
                ProgressDialog.hideProgressDialog()
                UtilityMethods.showToastMessage(mActivity!!, result.data?.msg)
                if (result.data!!.success)
                    {
                       /* mViewModel?.logoutApi(
                            sessionManager?.loginResult?.id
                        )*/
                    }

            }
        }
    }


    private fun handleLogoutApi(result: ApiResponse<CommonResponse>?) {
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
                if (result.data!!.success) {
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)
                    cacheDir.deleteRecursively()
                    sessionManager?.mLogout()

                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    )
                    finish()

                } else
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)

            }

        }
    }


}
