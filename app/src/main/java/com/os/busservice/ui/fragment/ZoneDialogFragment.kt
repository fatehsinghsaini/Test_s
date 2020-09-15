package com.os.busservice.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.model.zoneList.ZoneListResponse
import com.os.busservice.model.zoneList.ZoneResult
import com.os.busservice.ui.adapter.ZoneListAdapter


class ZoneDialogFragment : DialogFragment(), ZoneListAdapter.ClickListener{
//    var mCartViewModel: CartViewModel?=null
    companion object {
        var mTextView: TextView? = null

        fun newInstance(country: TextView): ZoneDialogFragment? {
            val f = ZoneDialogFragment()
            mTextView = country
            return f
        }

    }

    private lateinit var postalCodeAdapter: ZoneListAdapter
    private var mProgressBar: ProgressBar? = null
    val itemList = ArrayList<ZoneResult>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*
        mCartViewModel=ViewModelProvider(this).get(CartViewModel::class.java)

        mCartViewModel?.mGetZoneList(SessionManager.getInstance(context!!)?.loginResult?.id)

        mCartViewModel?.getZoneLiveData?.observe(this, Observer {
            handleApiCalling(it)
        })*/

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.zone_list_dialog, container, false)
        initView(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT)
    }

    private fun initView(view: View?) {

        val recyclerView = view?.findViewById<RecyclerView>(R.id.dialog_list)
        val closeDialog = view?.findViewById<TextView>(R.id.closeDialog)
        val et_search = view?.findViewById<EditText>(R.id.et_search)

        val pb_progress = view?.findViewById<ProgressBar>(R.id.pb_progress)

        et_search?.requestFocus()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        et_search?.isFocusable=true

        recyclerView?.itemAnimator = DefaultItemAnimator()
        postalCodeAdapter = ZoneListAdapter(
            activity!!,
            itemList, this, "1"
        )
        recyclerView?.adapter = postalCodeAdapter

        mProgressBar = pb_progress


        et_search?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.isNotEmpty()){
                    mSearchZone(editable.toString())
                }
                else {
                    postalCodeAdapter.updateList(itemList)
                    postalCodeAdapter.notifyDataSetChanged()
                }
            }
        })

        closeDialog?.setOnClickListener {
//            mCartViewModel?.dismissDialogFlag?.value=true
            dismiss()
        }

    }

    private fun mSearchZone(searchTxt: String) {
        val list=ArrayList<ZoneResult>()
        for(item in itemList){
            if(item.name.contains(searchTxt,true))
            {
                list.add(item)
            }
        }
        postalCodeAdapter.updateList(list)
        postalCodeAdapter.notifyDataSetChanged()
    }

    override fun mSearchItemClick(item: ZoneResult, type: String) {
        mTextView?.text=item.name
        mTextView?.tag=item.id

      /*  mCartViewModel?.dismissDialogFlag?.value=true
        AppDelegate.hideKeyBoard(activity)*/
        dismiss()
    }

    private fun handleApiCalling(result: ApiResponse<ZoneListResponse>?) {
        when (result!!.status) {
            ApiResponse.Status.ERROR -> {
                mProgressBar?.visibility = View.GONE

            }

            ApiResponse.Status.LOADING -> {
                mProgressBar?.visibility = View.VISIBLE
            }
            ApiResponse.Status.SUCCESS -> {
                mProgressBar?.visibility = View.GONE
                itemList.clear()
                itemList.addAll(result.data?.result as ArrayList<ZoneResult>)
                postalCodeAdapter.updateList(itemList)
                postalCodeAdapter.notifyDataSetChanged()
            }

        }

    }


}