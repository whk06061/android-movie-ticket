package woowacourse.movie

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class ActivityToolbarHelper(private val activity: AppCompatActivity) {
    fun setActionBar() {
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun onOptionsItemSelected(item: MenuItem, superOnItemSelected: Boolean): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity.finish()
                true
            }
            else -> superOnItemSelected
        }
    }
}
