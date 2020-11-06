package com.os.busservice.model.couponCode

data class CouponListResponse(
    val `data`: List<CouponData>,
    val msg: String,
    val status: Boolean
)