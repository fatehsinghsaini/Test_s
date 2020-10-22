package com.os.busservice.ui.activity

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.ActivityLoginBinding
import com.os.busservice.model.loginResponse.LoginResponse
import com.os.busservice.services.LocationUpdatesService
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.viewModel.LoginViewModel
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.Tags
import com.os.busservice.utility.UtilityMethods
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fb_google_layout.*
import kotlinx.android.synthetic.main.main_toolbar_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class LoginActivity : BaseBindingActivity(), View.OnClickListener {
    private var binding: ActivityLoginBinding? = null
    private var mViewModel: LoginViewModel? = null
    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding!!.emailEnable = false

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding?.mViewModel = mViewModel
    }

    override fun createActivityObject() {

        FacebookSdk.sdkInitialize(this)

    }

    override fun initializeObject() {
        mActivity = this

        mViewModel!!.error.observe(this, Observer {
            UtilityMethods.showToastMessage(mActivity!!, it)
        })

        mViewModel!!.loginLiveData.observe(this, Observer {
            handleLoginApi(it)
        })

        toolbarName.text = getString(R.string.sign_in)

    }

    override fun setListeners() {
        back.setOnClickListener(this)
        signUpTxt.setOnClickListener(this)
        forgotPwdTxt.setOnClickListener(this)
        bt_login.setOnClickListener(this)
        fbLayout.setOnClickListener(this)
        googleLayout.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        if(sessionManager?.mLat=="0.0" && sessionManager?.mAddress!!.isEmpty()){
            if (!hasPermissions(this))
                requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
            //get current location
            startService(Intent(this, LocationUpdatesService::class.java))
            bindService(
                Intent(this, LocationUpdatesService::class.java), mServiceConnection,
                Context.BIND_AUTO_CREATE
            )

            GlobalScope.launch(Dispatchers.Main){
                delay(3000)
                mService?.requestLocationUpdates()
            }


            myReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent) {
                    val location: Location? =
                        intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION)
                    mLat = location?.latitude
                    mLng = location?.longitude

                    sessionManager?.mLat=mLat.toString()
                    sessionManager?.mLng=mLng.toString()

                    GlobalScope.launch(Dispatchers.Main) {
                        if(mLat!=null){
                            val mGeocoder = Geocoder(applicationContext)
                            val mAddress=  mGeocoder.getFromLocation(mLat!!, mLng!!,1)
                            if(mAddress!=null && mAddress.size >0){
                                val mAddress=mAddress[0].getAddressLine(0)
                                sessionManager?.mAddress = mAddress
                            }
                        }
                    }
                    AppDelegate.Log("LatLng==",location.toString())
                }
            }
        }


    }
    override fun onClick(p0: View?) {
        AppDelegate.hideKeyBoard(this)

        when (p0?.id) {
            R.id.signUpTxt -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
            R.id.forgotPwdTxt -> {
                startActivity(Intent(this, ForgotPwdActivity::class.java))
                finish()
            }
            R.id.bt_login ->
                mViewModel!!.loginApi(sessionManager!!.mFCMToken)

            R.id.fbLayout -> faceBookLogin(Tags.LOGIN)
            R.id.googleLayout -> googlePlusLogin(Tags.LOGIN)
            R.id.back ->finish()
        }

    }

    private fun handleLoginApi(result: ApiResponse<LoginResponse>?) {
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
    }


    override fun onDestroy() {
        super.onDestroy()
        LoginManager.getInstance().logOut()
    }


    // A reference to the service used to get location updates.
    private var mService: LocationUpdatesService? = null
    private var mBound = false

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private var myReceiver: BroadcastReceiver? = null
    private var mLat:Double? = 0.0
    private var mLng:Double? = 0.0

    // Monitors the state of the connection to the service.
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
            val binder = service as LocationUpdatesService.LocalBinder
            mService = binder.service
            mBound = true

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mService = null
            mBound = false
        }
    }

    override fun onPause() {
        super.onPause()
        myReceiver?.let { LocalBroadcastManager.getInstance(this).unregisterReceiver(it) }
    }

    override fun onStop() {
        super.onStop()
        if( mService!=null){
            unbindService(mServiceConnection)
            mBound = false
            mService?.removeLocationUpdates()
        }

    }

    override fun onResume() {
        super.onResume()
        if(myReceiver!=null)
            LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver!!,
                IntentFilter(LocationUpdatesService.ACTION_BROADCAST))
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finishAffinity()
    }


}