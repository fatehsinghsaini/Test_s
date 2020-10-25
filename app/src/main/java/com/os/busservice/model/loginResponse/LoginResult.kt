package com.os.busservice.model.loginResponse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResult(
    val _id: String?,
    var country_code: String?,
    var email: String?,
    var fb_id: String?,
    var first_name: String?,
    var google_id: String?,
    val id: String?,
    var last_name: String?,
    var mobile: String?,
    var password: String?,
    val photo: String?,
    val referral_code: String?,
    val referred_by: String?,
    val referred_by_customer: String?,
    val status: String?,
    val token: String?,
    val twitter_id: String?,
    val unique_id: Int?,
    var otp: String?,
    val user_image: String?
):Parcelable