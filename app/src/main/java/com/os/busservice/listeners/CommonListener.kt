package com.os.busservice.listeners

import com.os.busservice.model.address.AddressResult


interface CommonListener {
 fun mOnItemClick(item:AddressResult?)
}