package com.os.busservice.fcm

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.os.busservice.R
import com.os.busservice.model.MessageEvent
import com.os.busservice.ui.activity.SplashActivity
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.Tags
import org.greenrobot.eventbus.EventBus


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        AppDelegate.Log("Message==", remoteMessage.notification.toString())

        if (remoteMessage.notification != null) {
            var title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body
            if (title == null || title == "") {
                title = resources.getString(R.string.app_name)
            }

            var notificationType =""
            if(remoteMessage.data.containsKey("type"))
             notificationType = remoteMessage.data["type"]!!

            val receiver_id = remoteMessage.data["receiver_id"]
            val receiver_token = remoteMessage.data["receiver_token"]
            val ORDERS = remoteMessage.data["ORDERS"]
            val cartId =
                if (remoteMessage.data.containsKey("order_id")) remoteMessage.data["order_id"] else ""

            AppDelegate.Log("Notification::","cardId:$cartId type:$notificationType")

            var intent = Intent(this, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            val statusArray = arrayOf("deliveryBoyArrived","deliveryStatus","orderDelivered","ordersuccess")

            if (statusArray.contains(notificationType)&& cartId!!.isNotEmpty()) {

                AppDelegate.Log("Notification::","notificationOrderDetails")

                EventBus.getDefault().post(MessageEvent(cartId,notificationType))
               /* intent = Intent(this, OrderHistoryDetailsActivity::class.java).putExtra(
                    Tags.DATA,
                    cartId
                )*/
            } else if (receiver_id != null && receiver_id.isNotEmpty() && notificationType.equals("chat", true))
                intent =
                    Intent(this, SplashActivity::class.java).putExtra(Tags.RECEIVER_ID, receiver_id)
                        .putExtra(
                            Tags.RECEIVER_TOKEN, receiver_token
                        ).putExtra(Tags.ORDERS, ORDERS)

            val genrator = NotificationGenrator()
            genrator.generateNotification(this, title, body, "12545", intent, "alert")


        }
    }

    override fun onNewToken(s: String) {


    }

}
