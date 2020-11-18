package com.os.busservice.ui.activity

import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.os.busservice.R
import com.os.busservice.databinding.BusSeatActivityBinding
import com.os.busservice.model.busListResponse.BusRouteData
import com.os.busservice.ui.adapter.pager.SeatPagerAdapter
import com.os.busservice.ui.baseFile.ActivityFromFragmentCallack
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.Tags
import kotlinx.android.synthetic.main.bottom_bus_seat_selection.*
import kotlinx.android.synthetic.main.bus_seat_toolbar.*
import kotlinx.android.synthetic.main.main_toolbar_layout.*
import kotlinx.android.synthetic.main.main_toolbar_layout.back

class BusSeatActivity :BaseBindingActivity(), ActivityFromFragmentCallack,TabLayout.OnTabSelectedListener {
    private var mBinding: BusSeatActivityBinding?=null

    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.bus_seat_activity)
    }

    override fun createActivityObject() {

    }

    override fun initializeObject() {
        mActivity=this

        var mBusData: BusRouteData?=null
        if(intent.hasExtra(Tags.DATA))
            mBusData= intent.getParcelableExtra(Tags.DATA)
        //set values
        bus_date.text = mBusData?.created_at
        bus_name.text = mBusData?.name
        bus_type.text = mBusData?.bus_type?.name


        mBinding!!.viewPager.adapter = SeatPagerAdapter(supportFragmentManager,lifecycle)
        TabLayoutMediator(mBinding!!.tabLayout, mBinding!!.viewPager) { tab, position ->
            mBinding!!.viewPager.setCurrentItem(tab.position, true)
            AppDelegate.tabsStyle(tab,R.style.TextStyleNormal)
            when (position) {
                0 -> {
                    tab.text = getString(R.string.lower)
                    AppDelegate.tabsStyle(tab,R.style.TextStyleNormalTabsSelected)
                }
                else -> tab.text = getString(R.string.upper)
            }

        }.attach()
        mBinding!!.tabLayout.addOnTabSelectedListener(this)


    }

    override fun setListeners() {
        back.setOnClickListener { finish() }
        priceSubmit.setOnClickListener { AppDelegate.mStartActivity(mActivity!!,0,PickupDropUpActivity()) }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        AppDelegate.tabsStyle(tab,R.style.TextStyleNormalTabsSelected)

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        AppDelegate.tabsStyle(tab,R.style.TextStyleNormalTabs)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

}