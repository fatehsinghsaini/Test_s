package com.os.busservice.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.app.ShareCompat
import com.os.busservice.R
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.utility.AppDelegate
import kotlinx.android.synthetic.main.activity_refer_and_earn.*
import kotlinx.android.synthetic.main.main_toolbar_layout.*


class ReferAndEarnActivity : BaseBindingActivity()  {
    override fun setBinding() {
     setContentView(R.layout.activity_refer_and_earn)
    }

    override fun createActivityObject() {
        mActivity=this
    }

    override fun initializeObject() {
        toolbarName.text = getString(R.string.coupon_code)
        couponCode.text="FADFAD4"
    }

    override fun setListeners() {
        back.setOnClickListener { finish() }
        copyTextLL.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(getString(R.string.copied_text), couponCode.text.toString())
            clipboard.setPrimaryClip(clip)
            AppDelegate.showToast(mActivity,getString(R.string.text_copy_successfully))
        }

        llShare.setOnClickListener {
            val mShareText="Share Referral code for referral \n ${couponCode.text.toString()}"

            ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle("Chooser title")
                .setText(mShareText)
                .startChooser();
        }


    }

}