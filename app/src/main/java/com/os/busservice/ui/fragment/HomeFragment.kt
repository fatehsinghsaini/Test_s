package com.os.busservice.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.os.busservice.R
import com.os.busservice.databinding.HomeFragmentBinding
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.ui.activity.BusSearchListing
import com.os.busservice.ui.activity.DashBoardActivity
import com.os.busservice.ui.adapter.ImagePagerAdapter
import com.os.busservice.ui.adapter.RecentBookingAdapter
import com.os.busservice.ui.baseFile.BaseFragment
import com.os.busservice.utility.HorizontalMarginItemDecoration
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import java.lang.Math.abs


class HomeFragment : BaseFragment<DashBoardActivity>(),CommonListener, View.OnClickListener{
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
    }

    override fun setListeners() {

        bt_search.setOnClickListener(this)

    }

    override fun mOnItemClick(item: AddressResult?) {

    }

    override fun onClick(p0: View?) {

        when(p0?.id){
            R.id.bt_search -> startActivity(Intent(mActivity,BusSearchListing::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }


    }


}