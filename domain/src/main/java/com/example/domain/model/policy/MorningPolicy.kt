package com.example.domain.model.policy

import com.example.domain.model.price.Price
import java.time.LocalDate
import java.time.LocalTime

class MorningPolicy : DiscountPolicy() {
    override fun getDiscountPrice(price: Price): Price {
        return Price(price.price - 2000)
    }

    override fun isAvailable(date: LocalDate, time: LocalTime): Boolean {
        return time.hour < 11
    }
}
