package com.os.busservice.ui.baseFile
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.SessionManager

abstract class BaseFragment<T : ActivityFromFragmentCallack> : Fragment() {

    protected var sessionManager: SessionManager? = null

    protected var mActivity: Activity? = null

    protected var mActivityFromFragmentCallack: T? = null

    protected abstract fun setBinding( inflater:LayoutInflater,container:ViewGroup? ): ViewDataBinding

    protected abstract fun createActivityObject()

    protected abstract fun initializeObject()

    protected abstract fun setListeners()
    var TagValue="Fragment"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActivityFromFragmentCallack) {
            mActivityFromFragmentCallack = context as T
        } else {
            throw ClassCastException("$context must implement ActivityFromFragmentCallack")
        }
        AppDelegate.Log(TagValue,"onAttach")

    }

    override fun onResume() {
        super.onResume()
        if (mActivity == null) throw NullPointerException("must create activty object")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = setBinding(inflater,container)
        val view = binding.root

        AppDelegate.Log(TagValue,"onCreateView")
        sessionManager= SessionManager.getInstance(activity!!)
        createActivityObject()
        initializeObject()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        AppDelegate.Log(TagValue,"onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        AppDelegate.Log(TagValue,"onStart")
    }

    override fun onStop() {
        super.onStop()
        AppDelegate.Log(TagValue,"onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        AppDelegate.Log(TagValue,"onDestroy")
    }

}
