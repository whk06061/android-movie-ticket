package woowacourse.movie.model

import woowacourse.movie.model.policy.MorningPolicy
import woowacourse.movie.model.policy.MovieDayPolicy
import woowacourse.movie.model.policy.NightPolicy
import java.time.LocalDate
import java.time.LocalTime

object PriceCalculator {
    private val policies = listOf(MovieDayPolicy(), MorningPolicy(), NightPolicy())
    fun calculate(
        price: Price,
        playingDate: LocalDate,
        playingTime: LocalTime
    ): Price {
        var calculatePrice = price
        for (policy in policies) {
            calculatePrice = policy.calculate(playingDate, playingTime, calculatePrice)
        }
        return calculatePrice
    }
}