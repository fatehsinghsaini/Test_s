package com.os.busservice.model

data class PaytmResponse(
    val BANKNAME: String,
    val BANKTXNID: String,
    val CHECKSUMHASH: String,
    val CURRENCY: String,
    val GATEWAYNAME: String,
    val MID: String,
    val ORDERID: String,
    val PAYMENTMODE: String,
    val RESPCODE: String,
    val RESPMSG: String,
    val STATUS: String,
    val TXNAMOUNT: String,
    val TXNDATE: String,
    val TXNID: String
)