package com.os.busservice.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.MyaccountFragmentBinding
import com.os.busservice.model.CommonResponse
import com.os.busservice.ui.activity.*
import com.os.busservice.ui.baseFile.BaseFragment
import com.os.busservice.ui.viewModel.LoginViewModel
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.UtilityMethods
import kotlinx.android.synthetic.main.myaccount_fragment.*


class MyAccountFragment : BaseFragment<DashBoardActivity>(), View.OnClickListener {
    private var mView: MyaccountFragmentBinding?=null
    private var mLoginViewModel: LoginViewModel?=null

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        mView=DataBindingUtil.inflate(inflater, R.layout.myaccount_fragment,container,false)
        return mView!!
    }

    override fun createActivityObject() {
        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        mLoginViewModel?.logoutLiveData?.observe(this, Observer {
            handleLogoutApi(it)
        })
    }

    override fun initializeObject() {
       mActivity=activity
        mView?.mSession =sessionManager



    }

    override fun setListeners() {
        manageAddress.setOnClickListener(this)
        changePwd.setOnClickListener(this)
        editProfile.setOnClickListener(this)
        logout.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        if (p0 == null) {
          /*  mLoginViewModel?.logoutApi(
                sessionManager?.loginResult?.id
            )*/

            sessionManager?.mLogout()
            startActivity(Intent(mActivity, LoginActivity::class.java))
            activity?.finish()
            return
        }

        when(p0?.id){
            R.id.manageAddress -> startActivity(Intent(mActivity, AddressListActivity::class.java))
            R.id.changePwd ->   startActivity(Intent(mActivity, ChangePwdActivity::class.java))
            R.id.editProfile ->   startActivity(Intent(mActivity, EditProfileActivity::class.java))
            R.id.logout ->   AppDelegate.showAlert(
                mActivity!!,
                getString(R.string.logout),
                getString(R.string.want_to_logout),
                this
            )
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
                    activity!!.cacheDir!!.deleteRecursively()
                    sessionManager?.mLogout()

                    startActivity(
                        Intent(
                            activity,
                            LoginActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    )
                    activity!!.finish()


                } else
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)

            }

        }
    }

}