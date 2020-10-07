package com.os.busservice.model.seat

 abstract class AbstractItem(val type: Int) {
    companion object {
        const val TYPE_CENTER = 0
        const val TYPE_EDGE = 1
        const val TYPE_EMPTY = 2
    }
}