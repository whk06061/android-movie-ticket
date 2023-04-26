package woowacourse.movie.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.ActivityToolbarHelper
import woowacourse.movie.R
import woowacourse.movie.model.TicketModel
import woowacourse.movie.util.parcelable

class TicketResultActivity : AppCompatActivity() {

    private lateinit var activityToolbarHelper: ActivityToolbarHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_result)

        initTicketDataView()
        activityToolbarHelper = ActivityToolbarHelper(this)
        activityToolbarHelper.setActionBar()
    }

    private fun initTicketDataView() {
        val ticketModel: TicketModel = getIntentTicketModel()
        findViewById<TextView>(R.id.text_title).text = ticketModel.reservationInfoModel.title
        findViewById<TextView>(R.id.text_playing_date).text = getString(
            R.string.date_time,
            ticketModel.reservationInfoModel.playingDate,
            ticketModel.reservationInfoModel.playingTime
        )
        findViewById<TextView>(R.id.text_count_seat).text = getString(
            R.string.count_seat_info,
            ticketModel.reservationInfoModel.count,
            ticketModel.seats.joinToString { it.row + it.column }
        )
        findViewById<TextView>(R.id.text_price_payment).text = getString(
            R.string.price_payment,
            ticketModel.price,
            ticketModel.reservationInfoModel.payment
        )
    }

    private fun getIntentTicketModel(): TicketModel = intent.parcelable(TICKET_KEY)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return activityToolbarHelper.onOptionsItemSelected(item, super.onOptionsItemSelected(item))
    }

    companion object {
        private const val TICKET_KEY = "ticket"
    }
}
