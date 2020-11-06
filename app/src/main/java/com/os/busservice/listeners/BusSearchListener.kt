package com.os.busservice.listeners

import com.os.busservice.model.address.AddressResult
import com.os.busservice.model.busListResponse.BusRouteData


interface BusSearchListener {
 fun mSeatSelected(item: BusRouteData?)
 fun mBusTrackingClick(item:BusRouteData?)
}