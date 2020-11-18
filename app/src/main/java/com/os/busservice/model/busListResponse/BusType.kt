package com.os.busservice.model.busListResponse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusType(
    val id: Int,
    val name: String,
    val type: String
):Parcelable