package com.os.busservice.listeners

import android.widget.TextView
import com.os.busservice.model.address.AddressResult


interface SafetyListener {
 fun mContactListing(textView: TextView)
}