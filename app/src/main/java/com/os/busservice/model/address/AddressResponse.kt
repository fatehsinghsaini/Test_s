package com.os.busservice.model.address

import com.os.busservice.model.address.AddressResult

data class AddressResponse(
    val msg: String,
    val result: List<AddressResult>,
    val success: Boolean
)