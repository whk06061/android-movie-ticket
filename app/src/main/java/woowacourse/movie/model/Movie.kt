package woowacourse.movie.model

import androidx.annotation.DrawableRes

data class Movie(
    @DrawableRes val image: Int,
    val title: String,
    val playingTimes: PlayingTimes,
    val runningTime: Int,
    val description: String
) : java.io.Serializable
