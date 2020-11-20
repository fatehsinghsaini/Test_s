package com.os.busservice.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.os.busservice.App
import com.os.busservice.R
import com.os.busservice.utility.AppDelegate


data class Transaction(
    val admin_comission: String,
    val amount: String,
    val id: String,
    val method: String,
    val order_id: String,
    val particulars: Any,
    val price: String,
    val product_name: String,
    val rewards: String,
    val transaction_id: String,
    val type: String,
    val created: String,
    val user_id: String
){

    fun mCreatedDate():String?{
        return AppDelegate.convertUTCToLocalShort(created,"yyyy-MM-dd HH:mm:ss","MMM d, yyyy HH:mm")
    }

    fun mRewards():String{
        return  if(type=="2")
            "-$${amount}"
          else "+$${amount}"

    }


    fun mAmountColor():Int{
      val ctx=  App.singleton
        return  if(type=="1")
            ContextCompat.getColor(ctx!!, R.color.colorGreen)
        else ContextCompat.getColor(ctx!!,R.color.colorRed)
    }

}