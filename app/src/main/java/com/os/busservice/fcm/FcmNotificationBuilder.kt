package com.os.busservice.fcm

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class FcmNotificationBuilder
private constructor() {
    private var mTitle: String? = null
    private var mMessage: String? = null
    private var mSenderUsername: String? = null
    private var receiverId: String? = ""
    private var receiverToken: String? = ""
    private var invoiceID: String? = ""
    private var mSenderUid: String? = null
    private var mSenderFirebaseToken: String? = null
    private var mReceiverFirebaseToken: String? = null
    private val customDataList =
        HashMap<String, String>()

    fun title(title: String?): FcmNotificationBuilder {
        mTitle = title
        return this
    }

    fun message(message: String?): FcmNotificationBuilder {
        mMessage = message
        return this
    }

    fun receiversId(receiversId: String?): FcmNotificationBuilder {
        receiverId = receiversId
        return this
    }

    fun invoiceId(invoiceId: String?): FcmNotificationBuilder {
        invoiceID = invoiceId
        return this
    }

    fun receiversToken(receiversToken: String?): FcmNotificationBuilder {
        receiverToken = receiversToken
        return this
    }

    fun username(username: String?): FcmNotificationBuilder {
        mSenderUsername = username
        return this
    }

    fun uid(uid: String?): FcmNotificationBuilder {
        mSenderUid = uid
        return this
    }

    fun firebaseToken(mSenderFirebaseToken: String?): FcmNotificationBuilder {
        this.mSenderFirebaseToken = mSenderFirebaseToken
        return this
    }

    fun receiverFirebaseToken(receiverFirebaseToken: String?): FcmNotificationBuilder {
        mReceiverFirebaseToken = receiverFirebaseToken
        return this
    }

    fun setCustomData(key: String, value: String?): FcmNotificationBuilder {
        customDataList[key] = value!!
        return this
    }

    fun send() {
        var requestBody: RequestBody? = null
        try {
            requestBody = RequestBody.create(
                MEDIA_TYPE_JSON,
                validJsonBody.toString()
            )
            Log.e("json", validJsonBody.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val request = Request.Builder()
            .addHeader(
                CONTENT_TYPE,
                APPLICATION_JSON
            )
            .addHeader(
                AUTHORIZATION,
                AUTH_KEY
            )
            .url(FCM_URL)
            .post(requestBody!!)
            .build()
        val call = OkHttpClient().newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(
                    TAG,
                    "onGetAllUsersFailure: " + e.message
                )
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) {
                Log.e(
                    TAG,
                    "onResponse: " + response.body!!.string()
                )
            }
        })
    }

    //for ios
    @get:Throws(JSONException::class)
    private val validJsonBody: JSONObject
        get() {
            val jsonObjectBody = JSONObject()
            jsonObjectBody.put(KEY_TO, mReceiverFirebaseToken)
            val jsonObjectBody_notification = JSONObject() //for ios
            val jsonObjectBody_notification_data = JSONObject() //for ios
            jsonObjectBody_notification.put(
                KEY_BODY,
                mMessage
            )
            jsonObjectBody_notification.put(
                KEY_TITLE,
                mSenderUsername
            )
            jsonObjectBody_notification_data.put(
                "receiver_id",
                receiverId
            )
            jsonObjectBody_notification_data.put(
                "receiver_token",
                receiverToken
            )
            jsonObjectBody_notification_data.put(
                "ORDERS",
                invoiceID
            )
            jsonObjectBody_notification_data.put(
                "type",
                "chat"
            )
            val jsonObjectData = JSONObject()
            // jsonObjectData.put(KEY_TITLE, mSenderUsername);     //for ios
            //  jsonObjectData.put(KEY_TEXT_MESSAGE, mMessage);
            for ((key, value) in customDataList) {
                jsonObjectData.put(key, value)
                Log.d("key=value", "$key=$value")
            }
            jsonObjectData.put("sound", "default")
            jsonObjectBody.put(KEY_DATA, jsonObjectData) // for android
            jsonObjectBody.put("data", jsonObjectBody_notification_data) // for android
            jsonObjectBody.put(
                KEY_NOTIFICATION_IOS,
                jsonObjectBody_notification
            ) //for ios
            return jsonObjectBody
        }

    companion object {

        val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        private const val TAG = "FcmNotificationBuilder"
        private const val SERVER_API_KEY =
            "AAAAKSC9fBE:APA91bH1FtK-5ky9K8R0NACmL82CBKE8dhh5O3PZnPTiu4422jkH2txdFdaMMmStPP628HWR_vuLIxD_eRmDOfwFbuq_r-kZTDJ3hYAr9EgTTK21IG3GeQMgo8hFa0ruRfjzanhwtKOD"
        private const val CONTENT_TYPE = "Content-Type"
        private const val APPLICATION_JSON = "application/json"
        private const val AUTHORIZATION = "Authorization"
        private const val AUTH_KEY =
            "key=$SERVER_API_KEY"
        private const val FCM_URL = "https://fcm.googleapis.com/fcm/send"

        // json related keys
        private const val KEY_TO = "to"
        private const val KEY_NOTIFICATION = "notification"
        private const val KEY_TITLE = "title" //sender name is title
        private const val KEY_BODY = "body"
        private const val KEY_TEXT_MESSAGE = "body"
        private const val KEY_DATA = "data"
        private const val KEY_NOTIFICATION_IOS = "notification"
        private const val KEY_SENDER_USERNAME = "username"
        private const val KEY_SENDER_UID = "uid"
        private const val KEY_SENDER_FCM_TOKEN = "fcm_token"
        const val KEY_OPEN_ACTIVITY_TYPE = "activity_type"
        fun initialize(): FcmNotificationBuilder {
            return FcmNotificationBuilder()
        }
    }
}