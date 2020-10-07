package com.os.busservice.listeners

import com.os.busservice.model.address.AddressResult


interface BusSearchListener {
 fun mSeatSelected(item:AddressResult?)
 fun mBusTrackingClick(item:AddressResult?)
}