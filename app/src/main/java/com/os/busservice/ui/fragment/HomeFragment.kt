package com.os.busservice.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.os.busservice.R
import com.os.busservice.databinding.HomeFragmentBinding
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.ui.activity.DashBoardActivity
import com.os.busservice.ui.adapter.ImagePagerAdapter
import com.os.busservice.ui.adapter.RecentBookingAdapter
import com.os.busservice.ui.baseFile.BaseFragment
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : BaseFragment<DashBoardActivity>(),CommonListener{
    private var mView: HomeFragmentBinding?=null
    private var mImagePagerAdapter: ImagePagerAdapter? = null
    private var mRecentBookAdapter: RecentBookingAdapter? = null

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        mView=DataBindingUtil.inflate(inflater, R.layout.home_fragment,container,false)
        return mView!!
    }

    override fun createActivityObject() {
    }

    override fun initializeObject() {
        mActivity=activity

        val mImageList = ArrayList<String>()
        mImageList.add("https://ibb.co/7nC2RrQ")
        mImageList.add("https://ibb.co/7nC2RrQ")

        mImagePagerAdapter = ImagePagerAdapter(activity!!, mImageList)
        view_pager.adapter = mImagePagerAdapter
        layoutDots.setViewPager(view_pager)

        mRecentBookAdapter = RecentBookingAdapter(this)

    }

    override fun setListeners() {

    }

    override fun mOnItemClick(item: AddressResult?) {

    }


}