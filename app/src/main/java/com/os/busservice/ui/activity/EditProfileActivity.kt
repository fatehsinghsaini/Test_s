package com.os.busservice.ui.activity

import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.os.busservice.R
import com.os.busservice.databinding.EditProfileActivityBinding
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.viewModel.MoreViewModel
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.ImagePickerUtility
import com.os.busservice.utility.UtilityMethods
import com.pepperonas.materialdialog.MaterialDialog
import kotlinx.android.synthetic.main.edit_profile_activity.*
import kotlinx.android.synthetic.main.main_toolbar_layout.*

import java.io.File

class EditProfileActivity : BaseBindingActivity(), ImagePickerUtility.ImagePickerCallback {
    private var mProfileImage: File? = null
    var mView: EditProfileActivityBinding? = null
    private var imagePickerUtil: ImagePickerUtility? = null
    private var mMoreViewModel: MoreViewModel? = null

    override fun setBinding() {
        mView = DataBindingUtil.setContentView(this, R.layout.edit_profile_activity)
        mView?.session = sessionManager
    }

    override fun createActivityObject() {
        mMoreViewModel = ViewModelProvider(this).get(MoreViewModel::class.java)
      /*  mMoreViewModel?.updateProfileLiveData?.observe(this, Observer {
            handleProfileApi(it)
        })*/

    }

    override fun initializeObject() {
        mActivity = this
        imagePickerUtil = ImagePickerUtility(this, this)

        mMoreViewModel?.firstName?.value = sessionManager?.loginResult?.first_name
        mMoreViewModel?.lastName?.value = sessionManager?.loginResult?.last_name
        mMoreViewModel?.email?.value = sessionManager?.loginResult?.email
        mMoreViewModel?.mobile?.value = sessionManager?.loginResult?.mobile_number

        mView?.mViewModel = mMoreViewModel

        toolbarName.text = getString(R.string.edit_profile)
    }

    override fun setListeners() {
        back.setOnClickListener {
            finish()
        }
        imgAddPhoto.setOnClickListener {
            showImageSelectionDialog(resources.getStringArray(R.array.image_selection))
        }

        buyNowBt.setOnClickListener {
            val firstName = firstName.text.toString().trim()
            val lastName = lastName.text.toString().trim()

            if (TextUtils.isEmpty(firstName)) {
                AppDelegate.showToast(this, getString(R.string.enter_your_name))
                return@setOnClickListener
            } else if (TextUtils.isEmpty(lastName)) {
                AppDelegate.showToast(this, getString(R.string.enter_last_name))
                return@setOnClickListener
            }

        /*    mMoreViewModel?.mUpdateEditProfile(
                sessionManager?.loginResult?.id,
                firstName,
                lastName,
                mProfileImage
            )*/
        }
    }

    override fun onImagePickSuccess() {
        val file = imagePickerUtil!!.imageFile
        if (file != null) {
            UtilityMethods.mLoadImage(profilePic, file)
            mProfileImage = file

            val path = imagePickerUtil!!.compressImage(imagePickerUtil!!.imagePath)
            mProfileImage = File(path)

        }
    }


    override fun onImagePickError(message: String?) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ImagePickerUtility.REQUEST_GALLARY || requestCode == ImagePickerUtility.REQUEST_CAMERA)
            imagePickerUtil!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePickerUtility.REQUEST_GALLARY || requestCode == ImagePickerUtility.REQUEST_CAMERA)
            imagePickerUtil!!.onActivityResult(requestCode, resultCode, data)

    }


    private fun showImageSelectionDialog(ITEMS: Array<String>) {
        MaterialDialog.Builder(mActivity!!).message(getString(R.string.select_option))
            .negativeText(getString(R.string.cancel))
            .listItems(true, *ITEMS)
            .itemClickListener(object : MaterialDialog.ItemClickListener() {
                override fun onClick(v: View?, position: Int, id: Long) {
                    super.onClick(v, position, id)
                    if (position == 0)
                        imagePickerUtil!!.captureImageFromCamera()
                    else
                        imagePickerUtil!!.chooseImageFromGallary()
                }
            })
            .show()
    }


   /* private fun handleProfileApi(result: ApiResponse<UpdateProfileResponse>?) {
        when (result!!.status) {
            ApiResponse.Status.ERROR -> {
                ProgressDialog.hideProgressDialog()
                UtilityMethods.showToastMessage(this, getString(R.string.error_network_connection))
            }
            ApiResponse.Status.LOADING -> ProgressDialog.showProgressDialog(this)
            ApiResponse.Status.SUCCESS -> {
                ProgressDialog.hideProgressDialog()
                val response = result.data
                UtilityMethods.showToastMessage(mActivity!!, response?.msg)

                if (result.data?.success!!) {
                    sessionManager?.profileImage = response?.result?.photo
                    sessionManager?.loginResult = response?.result

                    finish()
                }


            }
        }
    }*/

}