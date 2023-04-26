package woowacourse.movie.data

import woowacourse.movie.model.MovieListItem

interface DataRepository {

    fun getData(): List<MovieListItem>
}
