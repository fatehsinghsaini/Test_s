package com.os.busservice.model.loginResponse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
    val msg: String,
    val result: LoginResult?,
    val success: Boolean,
    val token: String?,
    val otp: String?,
    val accountNotExist: Boolean?
):Parcelable