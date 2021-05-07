package container.restaurant.android.presentation.feed.write

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivityFeedWriteBinding

class FeedWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedWriteBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            title = "용기낸 피드쓰기"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun getIntent(activity: AppCompatActivity) = Intent(activity, FeedWriteActivity::class.java)
    }
}