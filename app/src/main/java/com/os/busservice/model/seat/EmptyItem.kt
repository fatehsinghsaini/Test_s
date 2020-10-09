package com.os.busservice.model.seat

class EmptyItem(label: String?) : AbstractItem(label) {
    override fun getType(): Int {
        return TYPE_EMPTY
    }
}