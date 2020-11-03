package com.os.busservice.model.busListResponse

data class SeatListResponse(
    val result: List<Data>,
    val message: String,
    val success: Boolean
)