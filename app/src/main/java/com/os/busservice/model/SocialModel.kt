package com.os.busservice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SocialModel (var image: String = "",
                        var first_name: String = "",
                        var last_name: String = "",
                        var username: String = "",
                        var loginType: String = "",
                        var socialId: String = "",
                        var email_address: String = "",
                        var birthday: String = ""):Parcelable