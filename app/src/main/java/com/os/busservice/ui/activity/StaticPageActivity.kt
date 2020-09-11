package com.os.busservice.ui.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.Tags
import com.os.busservice.utility.UtilityMethods
import kotlinx.android.synthetic.main.activity_static_page.*
import kotlinx.android.synthetic.main.common_toolbar.*


class StaticPageActivity : BaseBindingActivity() {
    var pageName=""
    var pageSlug=""
/*    var mViewModel: MoreViewModel?=null*/
    override fun setBinding() {
        setContentView(R.layout.activity_static_page)
    }

    override fun createActivityObject() {
        mActivity=this
     /*   mViewModel=ViewModelProvider(this).get(MoreViewModel::class.java)*/


    }

    override fun initializeObject() {
      if(intent.hasExtra(Tags.TITLE))
          pageName= intent.getStringExtra(Tags.TITLE).toString()
        if(intent.hasExtra(Tags.DATA))
            pageSlug= intent.getStringExtra(Tags.DATA).toString()

     /*   mViewModel?.getSlugApi(pageSlug)
        mViewModel?.staticPageLiveData?.observe(this, Observer {
            handleApi(it)
        })*/

    }

    override fun setListeners() {
        toolbarName.text=pageName
        toolbarBackBt.setOnClickListener { finish() }

    }

 /*   private fun handleApi(result: ApiResponse<StaticPageResponse>?) {
        when (result!!.status) {
            ApiResponse.Status.ERROR -> {
                ProgressDialog.hideProgressDialog()
                UtilityMethods.showToastMessage(
                    mActivity!!,
                    getString(R.string.error_network_connection)
                )
            }

            ApiResponse.Status.LOADING -> ProgressDialog.showProgressDialog(mActivity!!)
            ApiResponse.Status.SUCCESS -> {
                ProgressDialog.hideProgressDialog()
                if (result.data?.success!!) {
                    mWebView.loadData(result.data.result[0].text, "text/html", "UTF-8")
                } else {
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg!!)
                }


            }


        }

    }*/




}