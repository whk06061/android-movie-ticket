package woowacourse.movie.data

import woowacourse.movie.R
import woowacourse.movie.model.MovieListItem

object MockAdRepository : DataRepository {
    override fun getData(): List<MovieListItem> = List(500) {
        MovieListItem.AdModel(R.drawable.advertisement, "https://techcourse.woowahan.com/")
    }
}
