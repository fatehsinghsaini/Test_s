package com.os.busservice.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.os.busservice.R
import com.os.busservice.databinding.GroupFragmentBinding
import com.os.busservice.databinding.OrderFragmentBinding
import com.os.busservice.databinding.PickupFragmentBinding
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.ui.activity.BusBookingActivity
import com.os.busservice.ui.activity.DashBoardActivity
import com.os.busservice.ui.activity.GroupDetailsActivity
import com.os.busservice.ui.adapter.GroupItemAdapter
import com.os.busservice.ui.adapter.OrderHistoryAdapter
import com.os.busservice.ui.adapter.pager.PickUpDropUpPagerAdapter
import com.os.busservice.ui.adapter.seat.PickupDropupAdapter
import com.os.busservice.ui.baseFile.BaseFragment
import com.os.busservice.utility.AppDelegate
import kotlinx.android.synthetic.main.pick_up_dropup_activity.*

class OrderFragment : BaseFragment<DashBoardActivity>(),CommonListener {
    private  var mBinging: OrderFragmentBinding?=null
    private var mAdapter: OrderHistoryAdapter?=null

    companion object {
        //1=online request,2=my offers
        var tabType = "1"

        @JvmStatic
        fun newInstance(
            tab_Type: String
        ): OrderFragment {
            tabType = tab_Type
            return OrderFragment()
        }
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        mBinging= DataBindingUtil.inflate(inflater, R.layout.order_fragment,container,false)
        return mBinging!!
    }

    override fun createActivityObject() {
        mAdapter = OrderHistoryAdapter(this)

    }

    override fun initializeObject() {
       mActivity =activity
        mBinging?.mAdapter =mAdapter
    }

    override fun setListeners() {

    }

    override fun mOnItemClick(item: AddressResult?) {

       /*AppDelegate.mStartActivity(mActivity!!,0,
            BusBookingActivity()
        )*/
    }
}