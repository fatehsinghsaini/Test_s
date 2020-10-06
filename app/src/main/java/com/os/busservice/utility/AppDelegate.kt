package com.os.busservice.utility


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.TextViewCompat
import com.google.android.material.tabs.TabLayout
import com.os.busservice.R
import com.os.busservice.utility.network.NetworkUtils

import java.sql.Timestamp
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.pow
import kotlin.math.roundToInt


object AppDelegate {

    lateinit var mAlert: AlertDialog.Builder

    fun getTimeStampFromDateServer(date1: String): Long? {
        var date: Date? = null
        try {
            // 2019-12-22 07:00:00
            val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            // DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'");
            // utcFormat.timeZone = TimeZone.getTimeZone("IST")
            date = utcFormat.parse(date1)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date!!.time
    }

    fun getDate(date1: String): Long? {
        var date: Date? = null
        try {
            // 2019-12-22 07:00:00
            val utcFormat = SimpleDateFormat("MMM d, yyyy HH:mm", Locale.getDefault())
            // DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'");
//            utcFormat.timeZone = TimeZone.getTimeZone("IST")
            date = utcFormat.parse(date1)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date!!.time
    }

    fun convertUTCToLocalShort(
        time: String,
        inputPattern: String,
        outPattern: String
    ): String? {


        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outPattern)

        var date: Date? = null
        var str: String? = null

        try {
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            outputFormat.timeZone = TimeZone.getDefault()
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return str
    }

    fun convertLocalToUTC(
        time: Date
    ): String? {
        val outPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val outputFormat = SimpleDateFormat(outPattern)

        var date: Date? = null
        var str: String? = null

        try {
            outputFormat.timeZone = TimeZone.getDefault()
//            date = inputFormat.parse(time)
            str = outputFormat.format(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return str
    }


    fun changeTimeToRelativeTime(timeStamp: Long): String {

        // it comes out like this 2013-08-31 15:55:22 so adjust the date format
        return try {

            DateUtils.getRelativeTimeSpanString(
                timeStamp,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS
            )
                .toString()
        } catch (e: Exception) {
            ""
        }

    }

    fun convertTimeFormat(time: String, fromFormat: String, toFormat: String): String {
        var timea = ""
        try {
            val sdf = SimpleDateFormat(fromFormat, Locale.getDefault())
            val dateObj = sdf.parse(time)
            println(dateObj)
            timea = SimpleDateFormat(toFormat, Locale.getDefault()).format(dateObj)
            return timea
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return timea
    }


    fun isValidEmail(target: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    //checking network availability
    fun isNetworkAvailable(context: Context): Boolean {
        if (NetworkUtils.isConnected()) {
            return true
        }
        showToast(context, context.getString(R.string.error_network_connection))
//        Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
        return false
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo = cm.activeNetworkInfo
//        return if (networkInfo != null && networkInfo.isConnected) {
//            true
//        } else false
    }


    fun getDate(time: Long, pattern: String): String {
        val c = Calendar.getInstance()
        c.timeInMillis = time
        val d = c.time
        val sdf = SimpleDateFormat(pattern)
        return sdf.format(d)
    }


    fun isValidPassword(pass2: String): Boolean {
        val PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[!@#$%&*]).{6,20})"
//        val PASSWORD_PATTERN = "((?=.*\\d).{6,20})"
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(pass2)
        return matcher.matches()
    }


    fun call(context: Context, number: String) {
        var uri = "tel:" + number.trim()
        var intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(uri)
        context.startActivity(intent)
    }


    fun showToast(mContext: Context?, Message: String) {
        try {
            if (mContext != null)
                Toast.makeText(mContext, Message, Toast.LENGTH_SHORT).show()
            else
                Log.e("tag", "lognActivity is null at showing toast.")
        } catch (e: Exception) {
            Log.e("tag", "lognActivity is null at showing toast.", e)
        }

    }

    fun convertTimestampToDate(time: Long): String {
        val stamp = Timestamp(time)
        val date = Date(stamp.getTime())
        val dateFormat = SimpleDateFormat("dd MMM, yyyy")
        return dateFormat.format(date)
    }

    fun showToast(mContext: Context?, Message: String, type: Int) {
        try {
            if (mContext != null)
                if (type == 0)
                    Toast.makeText(mContext, Message, Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(mContext, Message, Toast.LENGTH_LONG).show()
            else
                Log.e("tag", "lognActivity is null at showing toast.")
        } catch (e: Exception) {
            Log.e("tag", "lognActivity is null at showing toast.", e)
        }
    }

    fun HideKeyboardMain(mContext: Activity, view: View) {
        try {
            val imm = mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            // R.id.search_img
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            //Utility.debug(1, TAG, "Exception in executing HideKeyboardMain()");
            LogE(e)
        }

    }

    fun openKeyboard(mActivity: Activity, view: View) {
        try {
            val imm = mActivity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        } catch (e: Exception) {
            LogE(e)
        }

    }

    /**
     * @param TAG
     * @param Message
     * @param LogType
     */
    fun Log(TAG: String, Message: String, LogType: Int) {
        when (LogType) {
            // Case 1- To Show Message as Debug
            1 -> Log.d(TAG, Message)
            // Case 2- To Show Message as Error
            2 -> Log.e(TAG, Message)
            // Case 3- To Show Message as Information
            3 -> Log.i(TAG, Message)
            // Case 4- To Show Message as Verbose
            4 -> Log.v(TAG, Message)
            // Case 5- To Show Message as Assert
            5 -> Log.w(TAG, Message)
            // Case Default- To Show Message as System Print
            else -> println(Message)
        }
    }

    fun Log(TAG: String, Message: String) {
        Log(TAG, Message, 1)
    }

    /* Function to show log for error message */
    fun LogD(Message: String) {
        Log(Tags.DATE, "" + Message, 1)
    }

    /* Function to show log for error message */
    fun LogDB(Message: String) {
        Log(Tags.DATABASE, "" + Message, 1)
    }

    /* Function to show log for error message */
    fun LogE(e: Exception?) {
        if (e != null) {
            LogE(e.message!!)
            e.printStackTrace()
        } else {
            Log(Tags.ERROR, "exception object is also null.", 2)
        }
    }

    private fun LogE(message: String) {


    }

    /* Function to show log for error message */
    fun LogE(e: OutOfMemoryError?) {
        if (e != null) {
//            LogE(e.message!!)
            e.printStackTrace()
        } else {
            Log(Tags.ERROR, "exception object is also null.", 2)
        }
    }

    /* Function to show log for error message */
    fun LogE(message: String, exception: Exception?) {
        if (exception != null) {
            LogE(
                "from = " + message + " => "
                        + exception.message
            )
            exception.printStackTrace()
        } else {
            Log(Tags.ERROR, "exception object is also null. at " + message, 2)
        }
    }

    fun LogT(Message: String) {
        Log(Tags.TEST, "" + Message, 1)
    }

    fun LogCh(Message: String) {
        Log("check", "" + Message, 1)
    }

    fun LogT(Message: String, type: Int) {
        Log(Tags.TEST, "" + Message, type)
    }

    fun LogP(Message: String) {
        Log(Tags.PREF, "" + Message, 1)
    }

    /**
     * Funtion to log with tag GCM, you need to just pass the message.
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogGC(Message: String) {
        Log(Tags.GCM, "" + Message, 1)
    }

    /**
     * Funtion to log with tag Chat, you need to just pass the message.
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogC(Message: String) {
        Log(Tags.CHAT, "" + Message, 1)
    }

    fun hideKeyBoard(mActivity: Activity?) {
        if (mActivity == null)
            return
        else {
            val inputManager = mActivity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // check if no view has focus:
            val v = mActivity.currentFocus ?: return

            LogT("hideKeyBoard viewNot null")
            inputManager.hideSoftInputFromWindow(
                v.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    val currentTime: String
        get() = SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().time)


    fun rotateImage(angle: Int, OriginalPhoto: Bitmap): Bitmap {
        var OriginalPhoto = OriginalPhoto
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())

        OriginalPhoto = Bitmap.createBitmap(
            OriginalPhoto, 0, 0,
            OriginalPhoto.width, OriginalPhoto.height, matrix,
            true
        )
        return OriginalPhoto
    }

    fun isValidString(string: String?): Boolean {
        return string != null && !string.equals("null", ignoreCase = true) && string.isNotEmpty()
    }

    fun showAlert(
        mContext: Context,
        Title: String,
        Message: String,
        str_button_name: String,
        onClickListener: View.OnClickListener?
    ) {
        try {
            mAlert = AlertDialog.Builder(mContext)
            mAlert.setCancelable(false)
            mAlert.setTitle(Title)
            mAlert.setMessage(Message)
            mAlert.setPositiveButton(
                str_button_name
            ) { dialog, which ->
                onClickListener?.onClick(null)
                dialog.dismiss()
            }
            mAlert.show()
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun showAlert(
        mContext: Context,
        Title: String,
        Message: String,
        str_button_name: String,
        onClickListener: View.OnClickListener?,
        str_button_name_1: String
    ) {
        try {
            mAlert = AlertDialog.Builder(mContext)
            mAlert.setCancelable(false)
            mAlert.setTitle(Title)
            mAlert.setMessage(Message)
            mAlert.setPositiveButton(
                str_button_name
            ) { dialog, which ->
                onClickListener?.onClick(null)
                dialog.dismiss()
            }

            mAlert.setNegativeButton(
                str_button_name_1
            ) { dialog, which ->
//                onClickListener1?.onClick(null)
                dialog.dismiss()
            }
            mAlert.show()
        } catch (e: Exception) {
            LogE(e)
        }

    }


    fun getPriceFormatted(value: Int): String {
        val formatter = DecimalFormat("#,###,###")
        return formatter.format(value.toLong())
    }

    fun getDayOfMonthSuffix(n: Int): String {
        if (n >= 11 && n <= 13) {
            return "th"
        }
        when (n % 10) {
            1 -> return "st"
            2 -> return "nd"
            3 -> return "rd"
            else -> return "th"
        }
    }

    fun getDayOfMonthSuffix(string: String): String {
        try {
            val n = Integer.parseInt(string)
            if (n >= 11 && n <= 13) {
                return "th"
            }
            when (n % 10) {
                1 -> return "st"
                2 -> return "nd"
                3 -> return "rd"
                else -> return "th"
            }
        } catch (e: Exception) {
            LogE(e)
        }

        return "th"
    }

    fun roundOff(value: Double): Double {
        var value = value
        val factor = 10.0.pow(2.0).toLong()
        value *= factor
        val tmp = value.roundToInt()
        return tmp.toDouble() / factor
    }


    fun showAlert(
        mContext: Context,
        Title: String,
        Message: String,
        onClickListener: View.OnClickListener?
    ) {
        try {
            mAlert = AlertDialog.Builder(mContext)
            mAlert.setCancelable(false)
            mAlert.setTitle(Title)
            mAlert.setMessage(Message)
            mAlert.setPositiveButton(
                mContext.getString(R.string.no)
            ) { dialog, which ->
                dialog.dismiss()
            }
            mAlert.setNegativeButton(
                mContext.getString(R.string.yes)
            ) { dialog, which ->
                onClickListener?.onClick(null)
                dialog.dismiss()
            }
            mAlert.show()
        } catch (e: Exception) {
            LogE(e)
        }
    }

    fun mFormatDouble(amount:Double):String?{
        val decimalFormat = DecimalFormat("0.00")

        decimalFormat.format(amount)
       val str= String.format("%.2f",amount)

        return str

    }


    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun tabsStyle(tab: TabLayout.Tab?, mStyle:Int){
        val views = arrayListOf<View>()
        tab?.view?.findViewsWithText(views, tab.text, View.FIND_VIEWS_WITH_TEXT)
        views.forEach { view ->
            if (view is TextView) {
                TextViewCompat.setTextAppearance(view, mStyle)
            }
        }
    }


}

