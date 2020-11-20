package com.os.busservice.ui.adapter

import androidx.databinding.ViewDataBinding
import com.os.busservice.R
import com.os.busservice.databinding.WalletItemBinding
import com.os.busservice.model.Transaction
import java.util.*
import kotlin.collections.ArrayList

class WalletAdapter() : RecyclerBaseAdapter() {

    var dataItem=ArrayList<Transaction>()
    override fun getLayoutIdForPosition(position: Int) = R.layout.wallet_item

    override fun getViewModel(position: Int) = dataItem[position]

    override fun getItemCount() = 5

    override fun putViewDataBinding(viewDataBinding: ViewDataBinding) {
       /* if (viewDataBinding is WalletItemBinding) {

        }*/
    }

    fun updateList(dataItemList: ArrayList<Transaction>){
        this.dataItem=dataItemList

    }




}