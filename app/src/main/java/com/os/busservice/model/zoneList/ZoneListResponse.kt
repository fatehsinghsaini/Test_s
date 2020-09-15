package com.os.busservice.model.zoneList

data class ZoneListResponse(
    val msg: String,
    val result: List<ZoneResult>,
    val success: Boolean
)