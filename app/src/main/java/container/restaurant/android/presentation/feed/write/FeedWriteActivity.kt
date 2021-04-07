package container.restaurant.android.presentation.feed.write

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import container.restaurant.android.R

class FeedWriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_write)
    }

    companion object {
        fun getIntent(activity: AppCompatActivity) = Intent(activity, FeedWriteActivity::class.java)
    }
}