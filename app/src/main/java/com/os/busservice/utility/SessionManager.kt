package com.os.busservice.utility

import android.content.Context
import android.content.SharedPreferences
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.os.busservice.R
import com.os.busservice.model.loginResponse.LoginResult


class SessionManager : BaseObservable() {


    var mSourceLatLng : LatLng?
        get() =Gson().fromJson(shared!!.getString(Tags.mSourceLatLng, ""),LatLng::class.java)
        set(result){

            editor?.putString(Tags.mSourceLatLng,Gson().toJson(result))
            editor!!.commit()

        }

    var mDestinationLatLng : LatLng?
        get() =Gson().fromJson(shared!!.getString(Tags.mDestinationLatLng, ""),LatLng::class.java)
        set(result){
            editor?.putString(Tags.mDestinationLatLng,Gson().toJson(result))
            editor!!.commit()
        }

    var mSourceAddress: String?
        get() = shared!!.getString(
            Tags.mSourceAddress,
            ""
        )
        set(image){
            editor!!.putString(Tags.mSourceAddress, image)
            editor!!.commit()

        }
    var mDestinationAddress: String?
        get() = shared!!.getString(
            Tags.mDestinationAddress,
            ""
        )
        set(image){
            editor!!.putString(Tags.mDestinationAddress, image)
            editor!!.commit()

        }

    @get:Bindable("data")
    var loginResult: LoginResult?
        get() =Gson().fromJson(shared!!.getString(Tags.DATA, ""),LoginResult::class.java)
        set(result){

            editor?.putString(Tags.phone,result?.mobile_number)
            editor?.putString(Tags.email,result?.email)
            editor?.putString(Tags.image,result?.photo)
            editor?.putString(Tags.token,result?.auth_token)

            fullName =result?.first_name+" "+result?.last_name
            editor?.putString(Tags.DATA,Gson().toJson(result))
            editor!!.commit()
//            notifyPropertyChanged(BR.loginResult)

        }



    @get:Bindable("fullName")
    var fullName: String?
        get() = shared!!.getString(
            Tags.FirstName,
            ""
        )
        set(image){
            editor!!.putString(Tags.FirstName, image)
            editor!!.commit()
//            notifyPropertyChanged(BR.fullName)

        }
    @get:Bindable("email")
    var email: String?
        get() = shared!!.getString(
            Tags.email,
            ""
        )

        set(email){
            editor!!.putString( Tags.email, email)
            editor!!.commit()
//            notifyPropertyChanged(BR.fullName)

        }

    @get:Bindable("phone")
    var phone: String?
        get() = shared!!.getString(
            Tags.phone,
            ""
        )

        set(email){
            editor!!.putString( Tags.phone, email)
            editor!!.commit()
//            notifyPropertyChanged(BR.fullName)

        }

   @get:Bindable("profileImage")
    var profileImage: String?
        get() = shared!!.getString(
            Tags.image,
            ""
        )

        set(email){
            editor!!.putString( Tags.image, email)
            editor!!.commit()
//            notifyPropertyChanged(BR.profileImage)

        }


    var mFCMToken: String?
        get() = shared!!.getString(Tags.FCM,"")
        set(image){
            editor!!.putString(Tags.FCM, image)
            editor!!.commit()

        }
    @get:Bindable("mLat")
    var mLat: String?
        get() = shared!!.getString(Tags.mLat,"0.0")
        set(image){
            editor!!.putString(Tags.mLat, image)
            editor!!.commit()

        }
    @get:Bindable("mLng")
    var mLng: String?
        get() = shared!!.getString(Tags.mLng,"0.0")
        set(image){
            editor!!.putString(Tags.mLng, image)
            editor!!.commit()
        /*    notifyPropertyChanged(BR.fullName)*/

        }
    @get:Bindable("mAddress")
    var mAddress: String?
        get() = shared!!.getString(Tags.mCurrentAddress,"")
        set(image){
            editor!!.putString(Tags.mCurrentAddress, image)
            editor!!.commit()
//            notifyPropertyChanged(BR.mAddress)

        }
    var isLogin: Boolean?
        get() = shared!!.getBoolean(
            Tags.LOGIN,
            false
        )
        set(isLogin){
            editor!!.putBoolean(Tags.LOGIN, isLogin!!)
            editor!!.commit()

        }
    @get:Bindable("cartCount")
    var cartCount: Int
        get() = shared!!.getInt(
            Tags.CART_COUNT, 0)
        set(count){
            editor!!.putInt(Tags.CART_COUNT, count!!)
            editor!!.commit()
//            notifyPropertyChanged(BR.cartCount)
        }

    fun mSetStringValue(mKey:String,mValue:String){
        editor!!.putString(mKey, mValue)
        editor!!.commit()
    }
    fun mGetValue(mKey:String):String?{
       return shared?.getString(mKey, "")

    }

    companion object {
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
        private var shared: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null
        private var session: SessionManager? = null
        fun getInstance(context: Context): SessionManager? {
            if (session == null)
                session = SessionManager()

            if (shared == null) {
                shared = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                editor = shared!!.edit()
            }
            return session
        }
    }

    fun mLogout(){
       val token= mFCMToken
       val address= mAddress
        val lat =mLat
        val lng= mLng
        shared!!.edit().clear().apply()
        mFCMToken=token
        mAddress =address
        mLat =lat
        mLng =lng
    }
}