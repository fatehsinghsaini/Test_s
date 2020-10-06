package com.os.busservice.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.os.busservice.R
import com.os.busservice.databinding.GroupFragmentBinding
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.ui.activity.DashBoardActivity
import com.os.busservice.ui.activity.GroupDetailsActivity
import com.os.busservice.ui.adapter.GroupItemAdapter
import com.os.busservice.ui.baseFile.BaseFragment

class UpperBerthSeatFragment : BaseFragment<DashBoardActivity>(),CommonListener {
    private  var mBinging: GroupFragmentBinding?=null
    private var mAdapter:GroupItemAdapter?=null

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
        mBinging= DataBindingUtil.inflate(inflater, R.layout.group_fragment,container,false)
        return mBinging!!
    }

    override fun createActivityObject() {
        mAdapter = GroupItemAdapter(this)

    }

    override fun initializeObject() {
       mActivity =activity
        mBinging?.mAdapter =mAdapter
    }

    override fun setListeners() {

    }

    override fun mOnItemClick(item: AddressResult?) {
        startActivity(Intent(mActivity,GroupDetailsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
    }
}