package container.restaurant.android.presentation.feed.all

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivityFeedAllBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FeedAllActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedAllBinding

    private val viewModel: FeedAllViewModel by viewModel()

    private val mostFeedUserAdapter by lazy {
        MostFeedUserAdapter()
    }

    private val feedUserAdapter by lazy {
        FeedUserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityFeedAllBinding>(this, R.layout.activity_feed_all)
            .apply {
                lifecycleOwner = this@FeedAllActivity
                viewModel = this@FeedAllActivity.viewModel
            }

        with(viewModel) {
            close.observe(this@FeedAllActivity) {
                finish()
            }
            showHelpDialog.observe(this@FeedAllActivity) {
                FeedAllHelpDialogFragment().show(supportFragmentManager, "FeedAllHelpDialogFragment")
            }
        }

        setupMostFeedUserRecycler()
        setupFeedUserRecycler()
        // dummy
        mostFeedUserAdapter.setItems(
            listOf(
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level")
            )
        )
        feedUserAdapter.setItems(
            listOf(
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level"),
                FeedUser("", "neal", "level")
            )
        )
    }

    private fun setupMostFeedUserRecycler() {
        with(binding.rvMostFeedUser) {
            layoutManager = LinearLayoutManager(this@FeedAllActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mostFeedUserAdapter
        }
    }

    private fun setupFeedUserRecycler() {
        with(binding.rvFeedUser) {
            layoutManager = GridLayoutManager(this@FeedAllActivity, 4)
            adapter = feedUserAdapter
        }
    }

    companion object {

        fun getIntent(context: Context) = Intent(context, FeedAllActivity::class.java)
    }
}