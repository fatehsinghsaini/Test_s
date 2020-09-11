package com.os.busservice.utility

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.lang.ref.WeakReference
import java.util.*

class Utils private constructor() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var sApplication: Application? = null
        var sTopActivityWeakRef: WeakReference<Activity>? = null
        var sActivityList: MutableList<Activity> =
            LinkedList()
        private val mCallbacks: ActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                sActivityList.add(activity)
                setTopActivityWeakRef(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                setTopActivityWeakRef(activity)
            }

            override fun onActivityResumed(activity: Activity) {
                setTopActivityWeakRef(activity)
            }

            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(
                activity: Activity,
                bundle: Bundle
            ) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                sActivityList.remove(activity)
            }
        }

        /**
         * 初始化工具类
         *
         * @param app 应用
         */
        fun init(app: Application) {
            sApplication = app
            app.registerActivityLifecycleCallbacks(mCallbacks)
        }

        /**
         * 获取Application
         *
         * @return Application
         */
        @JvmStatic
        val app: Application?
            get() {
                if (sApplication != null) return sApplication
                throw NullPointerException("u should init first")
            }

        private fun setTopActivityWeakRef(activity: Activity) {
            if (sTopActivityWeakRef == null || activity != sTopActivityWeakRef!!.get()) {
                sTopActivityWeakRef =
                    WeakReference(activity)
            }
        }


        const val KEY_REQUESTING_LOCATION_UPDATES = "requesting_locaction_updates"


        /**
         * Returns the `location` object as a human readable string.
         * @param location  The [Location].
         */

        fun getLocationText(location: Location?): String? {
            return if (location == null) "Unknown location" else "(" + location.getLatitude().toString() + ", " + location.getLongitude().toString() + ")"
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun mCalling(phoneNo:String, context: Activity?){

            val array = arrayOf(Manifest.permission.CALL_PHONE)

            val intent=  Intent(Intent.ACTION_CALL)
            intent.setData(Uri.parse("tel:$phoneNo"))
            if (ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
               context.requestPermissions(array,102)
                return
            }
            context?.startActivity(intent)
        }


    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }





}