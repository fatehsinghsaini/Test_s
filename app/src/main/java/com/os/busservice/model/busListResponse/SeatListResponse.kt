package com.os.busservice.model.busListResponse

data class SeatListResponse(
    val result: List<BusRouteData>,
    val message: String,
    val success: Boolean
)