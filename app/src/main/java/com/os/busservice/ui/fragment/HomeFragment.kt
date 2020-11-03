package com.os.busservice.ui.fragment

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.facebook.login.LoginManager
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.datepicker.MaterialDatePicker
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.HomeFragmentBinding
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.model.busListResponse.SeatListRequest
import com.os.busservice.model.loginResponse.LoginResponse
import com.os.busservice.ui.activity.BusSearchListing
import com.os.busservice.ui.activity.DashBoardActivity
import com.os.busservice.ui.adapter.ImagePagerAdapter
import com.os.busservice.ui.adapter.RecentBookingAdapter
import com.os.busservice.ui.baseFile.BaseFragment
import com.os.busservice.ui.viewModel.HomeViewModel
import com.os.busservice.ui.viewModel.LoginViewModel
import com.os.busservice.utility.*
import com.os.busservice.utility.AppDelegate.currentTime
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment<DashBoardActivity>(),CommonListener, View.OnClickListener,
    DatePickerDialog.OnDateSetListener{
    private var mView: HomeFragmentBinding?=null
    private var mImagePagerAdapter: ImagePagerAdapter? = null
    private var mRecentBookAdapter: RecentBookingAdapter? = null
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private var mSourceLocationClick =false
    private var mSourceLatLng:LatLng?=null
    private var mDestinationLatLng:LatLng?=null
    private var mViewModel:HomeViewModel?=null


    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        mView=DataBindingUtil.inflate(inflater, R.layout.home_fragment,container,false)
        return mView!!
    }

    override fun createActivityObject() {
        Places.initialize(activity!!,getString(R.string.google_map_key))
        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mViewModel?.error?.observe(this, {
            AppDelegate.showToast(activity,it)
        })

    }

    override fun initializeObject() {
        mActivity=activity

        val mImageList = ArrayList<String>()
        mImageList.add("https://i.ibb.co/6NTdNd0/APSRTC-T-C-Page.jpg")
        mImageList.add("https://i.ibb.co/Vg92QMx/advantages-of-online-bus-ticket-booking-1-638.jpg")
        mImageList.add("https://i.ibb.co/Vg92QMx/advantages-of-online-bus-ticket-booking-1-638.jpg")

        val viewPager2=mView?.root?.view_pager
        mImagePagerAdapter = ImagePagerAdapter(activity!!, mImageList)
        viewPager2?.adapter = mImagePagerAdapter
        viewPager2?.offscreenPageLimit =1


        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))

        }
        viewPager2?.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            activity!!,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        viewPager2?.addItemDecoration(itemDecoration)

        mRecentBookAdapter = RecentBookingAdapter(this)
        mView?.mAdapter =mRecentBookAdapter

        //set current date
        mView?.root?.selectDate?.setText(AppDelegate.currentDate)
        sessionManager?.mSourceLatLng?.let {
            mView?.root?.sourceText?.setText( sessionManager?.mSourceAddress)
            mView?.root?.destinationText?.setText( sessionManager?.mDestinationAddress)
            mSourceLatLng =sessionManager?.mSourceLatLng
            mDestinationLatLng =sessionManager?.mDestinationLatLng
        }
    }

    override fun setListeners() {

        bt_search.setOnClickListener(this)
        sourceText.setOnClickListener(this)
        destinationText.setOnClickListener(this)
        selectDate.setOnClickListener(this)
        todayDate.setOnClickListener(this)
        tomarrowDate.setOnClickListener(this)
        changeSourceBt.setOnClickListener(this)


    }

    override fun mOnItemClick(item: AddressResult?) {

    }



    override fun onClick(p0: View?) {

        when(p0?.id){
            R.id.bt_search -> {
                mCallingBusListActivity(selectDate.text.toString())
            }
            R.id.sourceText ->{
                mSourceLocationClick =true
                mAutoCompleteIntent()
            }
            R.id.destinationText ->{
                mSourceLocationClick =false
                mAutoCompleteIntent()
            }
            R.id.selectDate -> {

                mDatePickerShow()
            }
            R.id.todayDate ->{
                mCallingBusListActivity(AppDelegate.currentDate)
            }
            R.id.tomarrowDate ->{
                val calendar =Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_MONTH,1)

                val mTomarrowDate= SimpleDateFormat("dd MMM, yyyy",Locale.getDefault()).format(calendar.time)
                mCallingBusListActivity(mTomarrowDate)
            }
            R.id.changeSourceBt -> {
                val rotation= if(changeSourceBt.rotation !=180f)
                    180f
                else
                    -180f
                changeSourceBt.animate().rotation(rotation).start()

                sessionManager?.mSourceAddress =destinationText.text.toString()
                sessionManager?.mDestinationAddress=sourceText.text.toString()

                sessionManager?.mSourceLatLng = mDestinationLatLng
                sessionManager?.mDestinationLatLng = mSourceLatLng

                mSourceLatLng =sessionManager?.mSourceLatLng
                mDestinationLatLng =sessionManager?.mDestinationLatLng

                sourceText.setText(sessionManager?.mSourceAddress)
                destinationText.setText(sessionManager?.mDestinationAddress)

            }
        }


    }

    private fun mCallingBusListActivity(startDate:String) {

        val sourceLoc = sourceText.text.toString()
        val destLoc = destinationText.text.toString()
        if (TextUtils.isEmpty(sourceLoc)) {
            mViewModel?.error?.value = getString(R.string.enter_source)
            return
        } else if (TextUtils.isEmpty(destLoc)) {
            mViewModel?.error?.value = getString(R.string.enter_destination)
            return
        }

        sessionManager?.mSourceAddress = sourceLoc
        sessionManager?.mDestinationAddress = destLoc

        sessionManager?.mSourceLatLng =mSourceLatLng
        sessionManager?.mDestinationLatLng =mDestinationLatLng

        val request = SeatListRequest()
        request.user_id =sessionManager?.loginResult?.user_id
        request.start_date = startDate
        request.start_time = currentTime
        request.start_lat = mSourceLatLng?.latitude.toString()
        request.start_lat = mSourceLatLng?.latitude.toString()

        request.end_lat = mDestinationLatLng?.latitude.toString()
        request.end_lng = mDestinationLatLng?.longitude.toString()
        request.start_point  = sourceLoc
        request.end_point  =   destLoc

        startActivity(Intent(mActivity,BusSearchListing::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT).putExtra(Tags.DATA,request))

    }

    private fun mDatePickerShow() {

        val now: Calendar = Calendar.getInstance()

        val dpd: DatePickerDialog = DatePickerDialog.newInstance(
            this,
            now.get(Calendar.YEAR),  // Initial year selection
            now.get(Calendar.MONTH),  // Initial month selection
            now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        )



        dpd.show(activity!!.supportFragmentManager, "Datepickerdialog")

    }

    private fun mAutoCompleteIntent(){
    val field = listOf(Place.Field.ID,Place.Field.NAME,Place.Field.LAT_LNG)
    val intent =Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,field).build(mActivity!!)
        startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)

                        if(mSourceLocationClick)
                        {
                            mSourceLatLng = place.latLng
                            sourceText.setText(place.name)
                        }
                        else {
                            mDestinationLatLng = place.latLng
                            destinationText.setText(place.name)
                        }

                        AppDelegate.Log("TAG", "Place: ${place.name}, ${place.id}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        AppDelegate.Log("TAG", status.statusMessage!!)
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

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
/*        this.year = year
        this.monthOfYear = monthOfYear
        this.dayOfMonth = dayOfMonth*/
        val date = year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth

      val formatedDate=  AppDelegate.convertUTCToLocalShort(date,"yyyy/MM/dd","dd MMM, yyyy")

        selectDate!!.setText(formatedDate)


    }






}