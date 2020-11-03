package com.os.busservice.model.busListResponse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeatListRequest(
    var end_date: String?=null,
    var end_lat: String?=null,
    var end_lng: String?=null,
    var end_point: String?=null,
    var end_time: String?=null,
    var start_date: String?=null,
    var start_lat: String?=null,
    var start_lng: String?=null,
    var start_point: String?=null,
    var start_time: String?=null,
    var user_id: String?=null
):Parcelable