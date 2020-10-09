package com.os.busservice.model.seat

class EdgeItem(label: String?) : AbstractItem(label) {
    override fun getType(): Int {
        return TYPE_EDGE
    }
}