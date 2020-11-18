package com.os.busservice.model.busListResponse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusRoute(
    val bus_stops: List<String>,
    val created_at: String,
    val end_point: String,
    val end_point_lat: String,
    val end_point_long: String,
    val id: Int,
    val name: String,
    val starting_point: String,
    val starting_point_lat: String,
    val starting_point_long: String
):Parcelable