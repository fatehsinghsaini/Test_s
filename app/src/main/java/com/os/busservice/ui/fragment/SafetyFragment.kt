package com.os.busservice.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.os.busservice.R
import com.os.busservice.databinding.GroupFragmentBinding
import com.os.busservice.databinding.OrderFragmentBinding
import com.os.busservice.databinding.PickupFragmentBinding
import com.os.busservice.databinding.SafetyFragmentBinding
import com.os.busservice.listeners.CommonListener
import com.os.busservice.listeners.SafetyListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.ui.activity.BusBookingActivity
import com.os.busservice.ui.activity.DashBoardActivity
import com.os.busservice.ui.activity.GroupDetailsActivity
import com.os.busservice.ui.adapter.GroupItemAdapter
import com.os.busservice.ui.adapter.OrderHistoryAdapter
import com.os.busservice.ui.adapter.SafetyAdapter
import com.os.busservice.ui.adapter.pager.PickUpDropUpPagerAdapter
import com.os.busservice.ui.adapter.seat.PickupDropupAdapter
import com.os.busservice.ui.baseFile.BaseFragment
import com.os.busservice.utility.AppDelegate
import kotlinx.android.synthetic.main.pick_up_dropup_activity.*

class SafetyFragment : BaseFragment<DashBoardActivity>(), SafetyListener {
    private  var mBinging: SafetyFragmentBinding?=null
    private var mAdapter: SafetyAdapter?=null
    private var mContactTextView:TextView?=null

    companion object {
        //1=online request,2=my offers
        var tabType = "1"

        @JvmStatic
        fun newInstance(
            tab_Type: String
        ): SafetyFragment {
            tabType = tab_Type
            return SafetyFragment()
        }
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        mBinging= DataBindingUtil.inflate(inflater, R.layout.safety_fragment,container,false)
        return mBinging!!
    }

    override fun createActivityObject() {
        mAdapter = SafetyAdapter(this)

    }

    override fun initializeObject() {
       mActivity =activity
        mBinging?.mAdapter =mAdapter
    }

    override fun setListeners() {

    }



    override fun mContactListing(textView: TextView) {
        mContactTextView =textView

    }
}