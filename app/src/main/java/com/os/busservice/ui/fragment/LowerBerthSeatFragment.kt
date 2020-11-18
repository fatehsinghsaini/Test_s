package com.os.busservice.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.os.busservice.R
import com.os.busservice.databinding.LowerBerthFragmentBinding
import com.os.busservice.databinding.UpperBerthFragmentBinding
import com.os.busservice.listeners.OnSeatSelected
import com.os.busservice.model.seat.AbstractItem
import com.os.busservice.model.seat.CenterItem
import com.os.busservice.model.seat.EdgeItem
import com.os.busservice.model.seat.EmptyItem
import com.os.busservice.ui.activity.BusSeatActivity
import com.os.busservice.ui.adapter.seat.LowerBusSeatAdapter
import com.os.busservice.ui.baseFile.BaseFragment
import com.os.busservice.utility.AppDelegate


class LowerBerthSeatFragment : BaseFragment<BusSeatActivity>(), OnSeatSelected {
    private var mBinging: LowerBerthFragmentBinding? = null
    private var mAdapter: LowerBusSeatAdapter? = null
    private val COLUMNS = 5

    companion object {
        //1=online request,2=my offers
        var tabType = "1"

        @JvmStatic
        fun newInstance(
            tab_Type: String
        ): LowerBerthSeatFragment {
            tabType = tab_Type
            return LowerBerthSeatFragment()
        }
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        mBinging =
            DataBindingUtil.inflate(inflater, R.layout.lower_berth_fragment, container, false)
        return mBinging!!
    }

    override fun createActivityObject() {
        val items: MutableList<AbstractItem> = ArrayList()
        for (i in 0..59) {
            if (i in 1..2)
                items.add(EmptyItem(i.toString()))   //for first line print
            else if (i in 55..59)                    //for last line print
                items.add(EdgeItem(i.toString()))
            else if (i % COLUMNS == 0 || i % COLUMNS == 4)
                items.add(EdgeItem(i.toString()))
            else if (i % COLUMNS == 1 || i % COLUMNS == 3)
                items.add(CenterItem(i.toString()))
            else
                items.add(EmptyItem(i.toString()))
        }
        mAdapter = LowerBusSeatAdapter(activity!!, items, this)

    }

    override fun initializeObject() {
        mActivity = activity
        val gridLayoutManager = GridLayoutManager(activity, COLUMNS)
        mBinging?.recyclerView?.layoutManager = gridLayoutManager
        mBinging?.mAdapter = mAdapter

    }

    override fun setListeners() {

    }

    override fun onSeatSelected(count: Int) {

    }

}