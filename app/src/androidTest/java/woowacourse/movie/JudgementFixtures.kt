package woowacourse.movie

import com.example.domain.model.model.Movie
import com.example.domain.model.model.Payment
import com.example.domain.model.model.ReservationInfo
import woowacourse.movie.mapper.toMovieModel
import woowacourse.movie.mapper.toReservationInfoModel
import java.time.LocalDate
import java.time.LocalTime

fun MovieModel() = Movie(
    R.drawable.movie_poster,
    "해리포터 2",
    LocalDate.of(2023, 3, 1),
    LocalDate.of(2023, 3, 31),
    152,
    "줄거리"
).toMovieModel()

fun ReservationInfoModel() = ReservationInfo(
    "해리포터 2",
    LocalDate.of(2023, 3, 1),
    LocalTime.of(9, 0),
    3,
    Payment.ON_SITE
).toReservationInfoModel()
