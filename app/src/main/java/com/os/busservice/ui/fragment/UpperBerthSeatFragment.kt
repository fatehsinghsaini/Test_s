package com.os.busservice.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.os.busservice.R
import com.os.busservice.databinding.UpperBerthFragmentBinding
import com.os.busservice.listeners.OnSeatSelected
import com.os.busservice.model.seat.AbstractItem
import com.os.busservice.model.seat.EdgeItem
import com.os.busservice.model.seat.EmptyItem
import com.os.busservice.ui.activity.BusSeatActivity
import com.os.busservice.ui.adapter.seat.UpperBusSeatAdapter
import com.os.busservice.ui.baseFile.BaseFragment
import com.os.busservice.utility.AppDelegate


class UpperBerthSeatFragment : BaseFragment<BusSeatActivity>(),OnSeatSelected {
    private  var mBinging: UpperBerthFragmentBinding?=null
    private var mAdapter: UpperBusSeatAdapter?=null
    private val COLUMNS = 4

    companion object {
        //1=online request,2=my offers
        var tabType = "1"

        @JvmStatic
        fun newInstance(
            tab_Type: String
        ): UpperBerthSeatFragment {
            tabType = tab_Type
            return UpperBerthSeatFragment()
        }
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        mBinging= DataBindingUtil.inflate(inflater, R.layout.upper_berth_fragment, container, false)
        return mBinging!!
    }

    override fun createActivityObject() {

        val items: MutableList<AbstractItem> = ArrayList()
        for (i in 0..23) {
            AppDelegate.Log("Seat Count",(i % COLUMNS).toString())
            if (i % COLUMNS == 0 || i % COLUMNS == 2 || i % COLUMNS == 3) {
                items.add(EdgeItem(i.toString()))
            } else {
                items.add(EmptyItem(i.toString()))
            }
        }

        mAdapter = UpperBusSeatAdapter(activity!!,items,this)

    }

    override fun initializeObject() {
       mActivity =activity

        val gridLayoutManager =GridLayoutManager(activity,COLUMNS)
        mBinging?.recyclerView?.layoutManager =gridLayoutManager
        mBinging?.mAdapter =mAdapter


    }

    override fun setListeners() {

    }

    override fun onSeatSelected(count: Int) {

    }

}