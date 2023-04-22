import com.example.domain.model.price.Price
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class MovieDayPolicyTest {

    @Test
    fun `매월 10일 상영일은 10% 할인된다`() {
        val actual = com.example.domain.model.policy.MovieDayPolicy()
            .calculate(
                LocalDate.of(2023, 4, 10), LocalTime.of(9, 0),
                Price()
            )
        val expected = Price(11700)
        assertEquals(actual, expected)
    }

    @Test
    fun `매월 20일 상영일은 10% 할인된다`() {
        val actual = com.example.domain.model.policy.MovieDayPolicy()
            .calculate(
                LocalDate.of(2023, 4, 20), LocalTime.of(9, 0),
                Price()
            )
        val expected = Price(11700)
        assertEquals(actual, expected)
    }

    @Test
    fun `매월 30일 상영일은 10% 할인된다`() {
        val actual = com.example.domain.model.policy.MovieDayPolicy()
            .calculate(
                LocalDate.of(2023, 4, 30), LocalTime.of(9, 0),
                Price()
            )
        val expected = Price(11700)
        assertEquals(actual, expected)
    }
}
