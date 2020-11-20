package com.os.busservice.model

data class WalletHistoryResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
){

    data class Data(
        val Transaction: List<Transaction>,
        val WalletBalance: String
    )
}