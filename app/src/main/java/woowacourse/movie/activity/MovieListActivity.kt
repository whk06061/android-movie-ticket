package woowacourse.movie.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.adapter.MovieListAdapter
import woowacourse.movie.data.MockAdRepository
import woowacourse.movie.data.MockMovieRepository
import woowacourse.movie.model.MovieListItem

class MovieListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = MovieListAdapter(
            getMovieListData()
        ) { item ->
            when (item) {
                is MovieListItem.MovieModel -> {
                    val intent = Intent(this@MovieListActivity, MovieDetailActivity::class.java)
                    intent.putExtra(MOVIE_KEY, item)
                    this@MovieListActivity.startActivity(intent)
                }
                is MovieListItem.AdModel -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                    this@MovieListActivity.startActivity(intent)
                }
            }
        }
        recyclerView.adapter = adapter
    }

    private fun getMovieListData(): List<MovieListItem> {
        val movies = MockMovieRepository.getData()
        val ads = MockAdRepository.getData()
        return movies.flatMapIndexed { movieIndex, movie ->
            if (checkAdItemOrder(movieIndex)) {
                val adIndex = (movieIndex / AD_CYCLE) % ads.size
                listOf(movie, ads[adIndex])
            } else listOf(movie)
        }
    }

    private fun checkAdItemOrder(movieIndex: Int): Boolean = (movieIndex + 1) % AD_CYCLE == 0

    companion object {
        private const val AD_CYCLE = 3
        const val MOVIE_KEY = "movie"
    }
}
