package woowacourse.movie

import junit.framework.TestCase.assertEquals
import org.junit.Test
import woowacourse.movie.domain.MorningPolicy
import woowacourse.movie.domain.Price
import java.time.LocalDate
import java.time.LocalTime

class MorningPolicyTest {
    @Test
    fun `오전 11시 이전 상영 시간은 2천원 할인된다`() {
        val actual = MorningPolicy().calculate(LocalDate.of(2023, 4, 11), LocalTime.of(9, 0), Price())
        val expected = Price(11000)
        assertEquals(actual, expected)
    }
}
