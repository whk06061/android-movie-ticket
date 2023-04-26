package woowacourse.movie.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.model.model.Movie
import com.example.domain.model.model.Payment
import com.example.domain.model.model.PlayingTimes
import com.example.domain.model.model.ReservationInfo
import woowacourse.movie.ActivityToolbarHelper
import woowacourse.movie.R
import woowacourse.movie.listener.DateSpinnerListener
import woowacourse.movie.mapper.toMovie
import woowacourse.movie.mapper.toReservationInfoModel
import woowacourse.movie.model.MovieListItem
import woowacourse.movie.model.ReservationInfoModel
import woowacourse.movie.util.parcelable
import java.time.LocalDate
import java.time.LocalTime

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var activityToolbarHelper: ActivityToolbarHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        val savedCount = getSavedCount(savedInstanceState)
        val savedDateSpinnerIndex = getSavedDateSpinnerIndex(savedInstanceState)
        val savedTimeSpinnerIndex = getSavedTimeSpinnerIndex(savedInstanceState)

        val movieModel: MovieListItem.MovieModel = getIntentMovieModel()
        initMovieDataView(movieModel)
        val movie: Movie = movieModel.toMovie()
        initSpinner(
            savedDateSpinnerIndex,
            savedTimeSpinnerIndex,
            movie.startDate,
            movie.endDate
        )
        initCountView(savedCount)
        initTicketingButton(movieModel.title)

        activityToolbarHelper = ActivityToolbarHelper(this)
        activityToolbarHelper.setActionBar()
    }

    private fun getIntentMovieModel(): MovieListItem.MovieModel = intent.parcelable(MovieListActivity.MOVIE_KEY)

    private fun initMovieDataView(movie: MovieListItem.MovieModel) {
        findViewById<ImageView>(R.id.img_movie).setImageResource(movie.image)
        findViewById<TextView>(R.id.text_title).text = movie.title
        findViewById<TextView>(R.id.text_playing_date).text = getString(
            R.string.playing_time, movie.startDate,
            movie.endDate
        )
        findViewById<TextView>(R.id.text_running_time).text =
            getString(R.string.running_time, movie.runningTime)
        findViewById<TextView>(R.id.text_description).text = movie.description
    }

    private fun initCountView(savedCount: Int) {
        findViewById<TextView>(R.id.text_count).text = savedCount.toString()
        initMinusButton()
        initPlusButton()
    }

    private fun initSpinner(
        savedDateSpinnerIndex: Int,
        savedTimeSpinnerIndex: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        initDateSpinner(savedDateSpinnerIndex, startDate, endDate)
        initTimeSpinner(savedTimeSpinnerIndex, startDate, endDate)
    }

    private fun initTicketingButton(movieTitle: String) {
        findViewById<Button>(R.id.btn_ticketing).setOnClickListener {
            startActivity(getIntentToSend(movieTitle))
        }
    }

    private fun getIntentToSend(movieTitle: String): Intent {
        val intent = Intent(this, ReserveSeatActivity::class.java)
        val reservationInfoModel = getReservationInfoModel(movieTitle)
        intent.putExtra(RESERVATION_INFO_KEY, reservationInfoModel)
        return intent
    }

    private fun initMinusButton() {
        findViewById<Button>(R.id.btn_minus).setOnClickListener {
            val countView = findViewById<TextView>(R.id.text_count)
            val count = getCount()
            if (count > 1) countView.text = (count.minus(1)).toString()
        }
    }

    private fun initPlusButton() {
        findViewById<Button>(R.id.btn_plus).setOnClickListener {
            val countView = findViewById<TextView>(R.id.text_count)
            val count = getCount()
            countView.text = (count.plus(1)).toString()
        }
    }

    private fun getReservationInfoModel(movieTitle: String): ReservationInfoModel {
        val spinnerDate = findViewById<Spinner>(R.id.spinner_date)
        val spinnerTime = findViewById<Spinner>(R.id.spinner_time)
        val playingDate = spinnerDate.selectedItem as LocalDate
        val playingTime = spinnerTime.selectedItem as LocalTime
        return ReservationInfo(
            movieTitle,
            playingDate,
            playingTime,
            getCount(),
            Payment.ON_SITE
        ).toReservationInfoModel()
    }

    private fun getSavedCount(savedInstanceState: Bundle?): Int =
        savedInstanceState?.getInt(SAVE_INSTANCE_COUNT_KEY) ?: DEFAULT_COUNT

    private fun getSavedDateSpinnerIndex(savedInstanceState: Bundle?): Int =
        savedInstanceState?.getInt(SAVE_INSTANCE_SPINNER_DATE_KEY) ?: DEFAULT_POSITION

    private fun getSavedTimeSpinnerIndex(savedInstanceState: Bundle?): Int =
        savedInstanceState?.getInt(SAVE_INSTANCE_SPINNER_TIME_KEY) ?: DEFAULT_POSITION

    private fun initTimeSpinner(
        savedTimeSpinnerIndex: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        val spinnerTime = findViewById<Spinner>(R.id.spinner_time)
        val spinnerDate = findViewById<Spinner>(R.id.spinner_date)
        val playingTimes = PlayingTimes(startDate, endDate)
        val selectedDate = playingTimes.playingDates[spinnerDate.selectedItemPosition]
        val times = playingTimes.getTimes(selectedDate)
        spinnerTime.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, times).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

        spinnerTime.setSelection(savedTimeSpinnerIndex)
    }

    private fun initDateSpinner(
        savedDateSpinnerIndex: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        val spinnerDate = findViewById<Spinner>(R.id.spinner_date)
        val spinnerTime = findViewById<Spinner>(R.id.spinner_time)
        val playingTimes = PlayingTimes(startDate, endDate)
        val dates = playingTimes.playingDates
        spinnerDate.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, dates).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

        spinnerDate.setSelection(savedDateSpinnerIndex, false)
        spinnerDate.onItemSelectedListener = DateSpinnerListener(playingTimes, dates, spinnerTime)
    }

    private fun getCount(): Int = findViewById<TextView>(R.id.text_count).text.toString().toInt()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val countText = findViewById<TextView>(R.id.text_count)
        outState.putInt(SAVE_INSTANCE_COUNT_KEY, countText.text.toString().toInt())
        val spinnerDate = findViewById<Spinner>(R.id.spinner_date)
        outState.putInt(SAVE_INSTANCE_SPINNER_DATE_KEY, spinnerDate.selectedItemPosition)
        val spinnerTime = findViewById<Spinner>(R.id.spinner_time)
        outState.putInt(SAVE_INSTANCE_SPINNER_TIME_KEY, spinnerTime.selectedItemPosition)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return activityToolbarHelper.onOptionsItemSelected(item, super.onOptionsItemSelected(item))
    }

    companion object {
        private const val SAVE_INSTANCE_COUNT_KEY = "COUNT"
        private const val SAVE_INSTANCE_SPINNER_DATE_KEY = "SPINNER_DATE"
        private const val SAVE_INSTANCE_SPINNER_TIME_KEY = "SPINNER_TIME"
        const val RESERVATION_INFO_KEY = "reservationInfo"
        private const val DEFAULT_COUNT = 1
        private const val DEFAULT_POSITION = 0
    }
}
