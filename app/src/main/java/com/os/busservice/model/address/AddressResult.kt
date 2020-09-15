package com.os.busservice.model.address

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressResult(
    val __v: Int?,
    val _id: String?,
    val address_name: String?,
    val appartment: String?,
    val createdAt: String?,
    val delivery_instructions: String?,
    val floor: String?,
    val house_number: String?,
    val is_default: String?,
    val is_delivery_available: Boolean?,
    val latitude: String?,
    val local_area: String?,
    val longitude: String?,
    val mobile: String?,
    val status: String?,
    val street: String?,
    val updatedAt: String?,
    val user_id: String?,
    val zone_id: String?,
    val zone_name: String?,
    val zone_number: String?
):Parcelable