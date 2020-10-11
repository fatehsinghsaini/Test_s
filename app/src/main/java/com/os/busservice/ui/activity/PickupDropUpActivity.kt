package com.os.busservice.ui.activity

import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.os.busservice.R
import com.os.busservice.databinding.BusSeatActivityBinding
import com.os.busservice.databinding.PickUpDropupActivityBinding
import com.os.busservice.ui.adapter.pager.PickUpDropUpPagerAdapter
import com.os.busservice.ui.adapter.pager.SeatPagerAdapter
import com.os.busservice.ui.baseFile.ActivityFromFragmentCallack
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.utility.AppDelegate
import kotlinx.android.synthetic.main.bottom_bus_seat_selection.*
import kotlinx.android.synthetic.main.main_toolbar_layout.*
import kotlinx.android.synthetic.main.pick_up_dropup_activity.*

class PickupDropUpActivity :BaseBindingActivity(), ActivityFromFragmentCallack,TabLayout.OnTabSelectedListener {
    private var mBinding: PickUpDropupActivityBinding?=null

    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.pick_up_dropup_activity)
    }

    override fun createActivityObject() {

    }

    override fun initializeObject() {
        mActivity=this


        mBinding!!.viewPager.adapter = PickUpDropUpPagerAdapter(supportFragmentManager,lifecycle)
        TabLayoutMediator(mBinding!!.tabLayout, mBinding!!.viewPager) { tab, position ->
            mBinding!!.viewPager.setCurrentItem(tab.position, true)
            AppDelegate.tabsStyle(tab,R.style.TextStyleNormal)
            when (position) {
                0 -> {
                    tab.text = getString(R.string.boarding)
                    AppDelegate.tabsStyle(tab,R.style.TextStyleNormalTabsSelected)
                }
                else -> tab.text = getString(R.string.dropping)
            }

        }.attach()
        mBinding!!.tabLayout.addOnTabSelectedListener(this)


    }

    override fun setListeners() {
        back.setOnClickListener { finish() }


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