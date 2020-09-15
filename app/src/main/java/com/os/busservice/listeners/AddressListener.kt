package com.os.busservice.listeners

import com.os.busservice.model.address.AddressResult


interface AddressListener {
 fun mOnItemClick(item:AddressResult?)
 fun mOnAddressSelect(item: AddressResult?)
}