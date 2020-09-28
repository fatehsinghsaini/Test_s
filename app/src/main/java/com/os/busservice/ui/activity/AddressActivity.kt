package com.os.busservice.ui.activity
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.model.CommonResponse
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.fragment.ZoneDialogFragment
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.UtilityMethods

import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_address.address
import kotlinx.android.synthetic.main.activity_address.bt_submit

import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class AddressActivity : BaseBindingActivity(),OnMapReadyCallback, GoogleMap.OnCameraMoveListener,GoogleMap.OnCameraIdleListener {
    private var mMap: GoogleMap? = null
    private var mSelectedLat:Double=0.0
    private var mSelectedLlng:Double=0.0
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)

    override fun setBinding() {
        setContentView(R.layout.activity_address)
    }

    override fun createActivityObject() {
     /*   mCartViewModel= ViewModelProvider(this).get(CartViewModel::class.java)

        mCartViewModel?.addAddressLiveData?.observe(this, Observer {
            addAddressApiResponse(it)
        })*/
    }

    override fun initializeObject() {
        mActivity=this

        mSelectedLat=sessionManager?.mLat!!.toDouble()
        mSelectedLlng=sessionManager?.mLng!!.toDouble()

//        search_bar.visibility= View.GONE
        cartFilterLL.visibility= View.GONE
        toolbarName.text=getString(R.string.add_address)

        homeType.isChecked=true

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        if (!hasPermissions(this)) {
            requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
        }else
            mInitializeMap()
    }

    override fun setListeners() {
        toolbarBackBt.setOnClickListener {
            AppDelegate.hideKeyBoard(mActivity)
            finish()
        }
        deliveryZone.setOnClickListener {
            val frgManager=supportFragmentManager.beginTransaction()
            val fragment= ZoneDialogFragment.newInstance(deliveryZone)
            fragment?.show(frgManager,"dialog")
        }

        bt_submit.setOnClickListener {
           /* val request = CommonRequest()
            request.language="en"
            request.user_id=sessionManager?.loginResult?.id
            request.latitude=mSelectedLat.toString()
            request.longitude=mSelectedLlng.toString()
            when {
                houseNo.text.toString().isEmpty() -> {
                    AppDelegate.showToast(this,getString(R.string.select_house_no))
                    return@setOnClickListener
                }
                deliveryZone.text.isNotEmpty() -> {
                    request.zone_id = deliveryZone.tag.toString()
                    request.zone_name=deliveryZone.text.toString()
                }
                else -> {
                    AppDelegate.showToast(this,getString(R.string.select_delivery_zone))
                    return@setOnClickListener
                }
            }





            val button= findViewById<RadioButton>(addressTypeRG.checkedRadioButtonId)
            request.address_name=button.text.toString()
            request.street=address.text.toString()
            request.mobile=sessionManager?.phone
            request.house_number=houseNo.text.toString()
            request.local_area=landmark.text.toString()
            request.delivery_instructions="test"
            request.appartment=""
            request.floor=""

            mCartViewModel?.mAddAddress(request)*/

        }


        address.setOnClickListener {
            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun mInitializeMap() {
        GlobalScope.launch(Dispatchers.Main) {
            val mGeocoder = Geocoder(applicationContext)
            val mAddress=  mGeocoder.getFromLocation(mSelectedLat, mSelectedLlng,1)
            if(mAddress!=null && mAddress.size >0){
                val mAddress=mAddress[0].getAddressLine(0)
                address.setText(mAddress)
            }

        }



        //initialize map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
        mMap?.isMyLocationEnabled = true

        with(mMap){
            this?.setOnCameraIdleListener (this@AddressActivity)
            this?.setOnCameraMoveListener(this@AddressActivity)

        }

        currentLocation(sessionManager?.mLat?.toDouble()?.let { LatLng(it,sessionManager?.mLng?.toDouble()!!) })
    }




    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull())
            {
                mInitializeMap()
            }
            else
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_LONG)
                    .show()
        }
    }


    private fun currentLocation(latLng: LatLng?) {
        if (mMap != null && latLng != null) {
            mMap?.clear()
            mMap?.addMarker(MarkerOptions().position(latLng))
            mMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(latLng, 15f)
            )
        }
    }

    override fun onCameraMove() {
        AppDelegate.Log("Location","camera move")
    }

    override fun onCameraIdle() {
        val mLoc=mMap?.cameraPosition?.target
        AppDelegate.Log("Location","onCameraIdle$mLoc")

        GlobalScope.launch(Dispatchers.Main) {
            val mGeocoder = Geocoder(applicationContext)
            val mAddress=        mLoc?.latitude?.let { mGeocoder.getFromLocation(it, mLoc.longitude,1) }
            if(mAddress!=null && mAddress.size >0){
                val mAddress=mAddress[0].getAddressLine(0)
                if(address.text.toString()!=mAddress)
                {
                    mSelectedLat=mLoc.latitude
                    mSelectedLlng=mLoc.longitude

                    address.setText(mAddress)

                    if (mMap != null) {
                        mMap?.clear()
                        mMap?.addMarker(MarkerOptions().position(mLoc))
                    }
                }
            }
        }
    }

    private fun addAddressApiResponse(result: ApiResponse<CommonResponse>?) {
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
                    UtilityMethods.showToastMessage(
                        mActivity!!,
                        result.data.msg
                    )
                    finish()
                }else
                    UtilityMethods.showToastMessage(
                        mActivity!!,
                        result.data.msg
                    )


            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        Log.i("TAG", "Place: ${place.name}, ${place.id}")
                        address.setText(place.address)
                        currentLocation(place.latLng)


                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i("TAG", status.toString())
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}