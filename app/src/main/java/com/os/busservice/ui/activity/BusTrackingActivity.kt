package com.os.busservice.ui.activity
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.os.busservice.R
import com.os.busservice.databinding.BusTrackingActivityBinding
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.ui.adapter.BusSearchItemAdapter
import com.os.busservice.ui.adapter.BusTrackingItemAdapter
import com.os.busservice.ui.baseFile.BaseBindingActivity
import kotlinx.android.synthetic.main.main_toolbar_layout.*

class BusTrackingActivity : BaseBindingActivity(), OnMapReadyCallback, GoogleMap.OnCameraMoveListener,GoogleMap.OnCameraIdleListener,CommonListener {

    private var mMap: GoogleMap? = null
    private var mBinding: BusTrackingActivityBinding?=null
    private lateinit var mAdapter: BusTrackingItemAdapter


    override fun setBinding() {
     mBinding= DataBindingUtil.setContentView(this,R.layout.bus_tracking_activity)
    }

    override fun createActivityObject() {
        mAdapter = BusTrackingItemAdapter(this)


    }

    override fun initializeObject() {
        mActivity =this
        mBinding?.mAdapter =mAdapter

        toolbarName.text = getString(R.string.track_bus)


    }

    override fun setListeners() {

        back.setOnClickListener{
            finish()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        if (!hasPermissions(this)) {
            requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
        }else
            mInitializeMap()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
        mMap?.isMyLocationEnabled = true

    }

    override fun onCameraMove() {
    }

    override fun onCameraIdle() {
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun mInitializeMap() {
    /*    GlobalScope.launch(Dispatchers.Main) {
            val mGeocoder = Geocoder(applicationContext)
            val mAddress=  mGeocoder.getFromLocation(mSelectedLat, mSelectedLlng,1)
            if(mAddress!=null && mAddress.size >0){
                val mAddress=mAddress[0].getAddressLine(0)
                address.setText(mAddress)
            }

        }*/



        //initialize map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun mOnItemClick(item: AddressResult?) {

    }


}