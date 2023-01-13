package com.survivalcoding.noteapp

import com.survivalcoding.noteapp.domain.util.OrderType

class Config {
    companion object {
        val DEFAULT_ORDER_TYPE = OrderType.DATE
        const val DEFAULT_IS_ASCENDING = false
    }
}