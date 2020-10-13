package com.os.busservice.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import bolts.Task.cancelled
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.os.busservice.R
import com.os.busservice.databinding.BookingHistoryFragmentBinding
import com.os.busservice.databinding.PickUpDropupActivityBinding
import com.os.busservice.databinding.SafetyPagerFragmentBinding
import com.os.busservice.ui.activity.DashBoardActivity
import com.os.busservice.ui.adapter.pager.OrderHistoryPagerAdapter
import com.os.busservice.ui.adapter.pager.SafetyPagerAdapter
import com.os.busservice.ui.baseFile.ActivityFromFragmentCallack
import com.os.busservice.ui.baseFile.BaseFragment
import com.os.busservice.utility.AppDelegate
import kotlinx.android.synthetic.main.main_toolbar_layout.*

class SafetyPagerFragment :BaseFragment<DashBoardActivity>(), ActivityFromFragmentCallack,TabLayout.OnTabSelectedListener {
    private var mBinding: SafetyPagerFragmentBinding?=null


    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.safety_pager_fragment,container,false)
        return mBinding!!
    }

    override fun createActivityObject() {

    }

    override fun initializeObject() {
        mActivity=activity
      /*  toolbarName.text ="Jaipur to Kota"*/

        mBinding!!.viewPager.adapter = SafetyPagerAdapter(activity!!.supportFragmentManager,lifecycle)
        TabLayoutMediator(mBinding!!.tabLayout, mBinding!!.viewPager) { tab, position ->
            mBinding!!.viewPager.setCurrentItem(tab.position, true)
            AppDelegate.tabsStyle(tab,R.style.TextStyleNormal)
            when (position) {
                0 -> {
                    tab.text = getString(R.string.send_location)
                    AppDelegate.tabsStyle(tab,R.style.TextStyleNormalTabsSelected)
                }
                else -> tab.text = getString(R.string.received_loation)
            }

        }.attach()
        mBinding!!.tabLayout.addOnTabSelectedListener(this)


    }

    override fun setListeners() {



    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        AppDelegate.tabsStyle(tab,R.style.TextStyleNormalTabsSelected)

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        AppDelegate.tabsStyle(tab,R.style.TextStyleNormalTabs)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }


    override fun changeFragment(fragment: Fragment, isAddToBackStack: Boolean) {

    }

    override fun changeToolbarTitle(title: String) {

    }

}