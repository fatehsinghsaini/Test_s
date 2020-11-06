package com.os.busservice.model.couponCode

data class CouponData(
    val discount_type: String,
    val id: Int,
    val offer_image: String,
    val promotion_value: String,
    val promotional_code: String,
    val title: String,
    val valid_from: String,
    val valid_to: String
)