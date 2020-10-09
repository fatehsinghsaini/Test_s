package com.os.busservice.model.seat

class CenterItem(label: String?) : AbstractItem(label!!) {
    override fun getType(): Int {
        return TYPE_CENTER
    }
}