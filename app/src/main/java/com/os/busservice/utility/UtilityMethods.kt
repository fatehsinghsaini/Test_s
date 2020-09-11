package com.os.busservice.utility


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.text.SpannableString
import android.text.TextUtils
import android.text.format.DateFormat
import android.text.style.UnderlineSpan
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.os.busservice.R
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object UtilityMethods {

    fun getDate(time: Long): String {

        val timeStamp = java.sql.Timestamp(time)
        val date: Date = timeStamp
        return DateFormat.format("yyyy-MM-dd", date).toString()
    }

    fun getTime(time: Long): String {

        val timeStamp = java.sql.Timestamp(time)
        val date: Date = timeStamp
        return DateFormat.format("hh:mm aa", date).toString()
    }

    fun getDateTime(time: Long): String {
        val timeStamp = java.sql.Timestamp(time)
        val date: Date = timeStamp
        return DateFormat.format("yyyy-MM-dd'T'HH:mm:ss", date).toString()
    }



    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun showToastMessage(activity: Activity, message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


    @BindingAdapter("thumbnail")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl).placeholder(R.mipmap.ic_launcher)
                .into(view)


    }

    @BindingAdapter("userprofile")
    @JvmStatic
    fun setImage(view: ImageView, loadImageUrllink: String?) {
        Glide.with(view.context)
            .load(loadImageUrllink).placeholder(R.mipmap.ic_launcher)
            .apply(RequestOptions().override(200, 200))
            .into(view)
    }


    fun mLoadImage(view: ImageView, loadImageUrllink: File) {
        Glide.with(view.context)
            .load(loadImageUrllink).placeholder(R.mipmap.ic_launcher)
            .apply(RequestOptions().override(200, 200))
            .into(view)
    }

    @BindingAdapter("loadWebdata")
    @JvmStatic
    fun setHtmlData(view: WebView, url: String) {
        view.loadData(url, "text/html", "UTF-8");
    }

    fun isLocationPermissionGranted(activity: Activity): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )
            return false
        return true
    }

    fun createPart(file: File?, imageParm:String): MultipartBody.Part? {
        var part: MultipartBody.Part? = null
        if (file != null) {
            val requestFile = file.asRequestBody("multipart/form-data".toMediaType())
            part = MultipartBody.Part.createFormData(imageParm, file.name, requestFile)
        }

        return part
    }


}
