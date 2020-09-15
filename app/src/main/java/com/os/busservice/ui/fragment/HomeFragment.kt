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
import com.os.busservice.ui.activity.DashBoardActivity
import com.os.busservice.ui.baseFile.BaseFragment


class HomeFragment : BaseFragment<DashBoardActivity>() {
    private var mView: HomeFragmentBinding?=null
    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        mView=DataBindingUtil.inflate(inflater, R.layout.home_fragment,container,false)
        return mView!!
    }

    override fun createActivityObject() {
    }

    override fun initializeObject() {
        mActivity=activity


    }

    override fun setListeners() {

    }


}