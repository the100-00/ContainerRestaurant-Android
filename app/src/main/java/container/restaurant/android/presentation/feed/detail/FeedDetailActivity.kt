package container.restaurant.android.presentation.feed.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import container.restaurant.android.databinding.ActivityFeedDetailBinding
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class FeedDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedDetailBinding

    private val commentAdapter by lazy {
        FeedCommentAdapter()
    }

    private val levelAdapter = LevelAdapter()
    private val mainMenuAdapter = MenuAdapter()
    private val subMenuAdapter = MenuAdapter()

    private val viewModel by viewModel<FeedDetailViewModel> {
        parametersOf(intent.getLongExtra(KEY_FEED_ID, -1))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedDetailBinding.inflate(layoutInflater)
            .apply {
                viewModel = this@FeedDetailActivity.viewModel
                lifecycleOwner = this@FeedDetailActivity
            }
        setContentView(binding.root)

        with(viewModel) {
            errorToast.observe(this@FeedDetailActivity) {
                Toast.makeText(this@FeedDetailActivity, getString(it ?: return@observe), Toast.LENGTH_SHORT).show()
            }
            feedComment.observe(this@FeedDetailActivity) {
                commentAdapter.addItem(it ?: return@observe)
            }
            feedComments.observe(this@FeedDetailActivity) {
                commentAdapter.setItems(it ?: emptyList())
            }
            level.observe(this@FeedDetailActivity) {
                levelAdapter.setItems(it ?: 0)
            }
            mainMenu.observe(this@FeedDetailActivity) {
                mainMenuAdapter.setItems(it ?: emptyList())
            }
            subMenu.observe(this@FeedDetailActivity) {
                subMenuAdapter.setItems(it ?: emptyList())
            }
        }

        setupCommentRecycler()
        setupLevel()
        setupMenu()
    }

    private fun setupMenu() {
        with(binding.rvMainFood) {
            layoutManager = LinearLayoutManager(this@FeedDetailActivity)
            adapter = mainMenuAdapter
        }
        with(binding.rvSideDishFood) {
            layoutManager = LinearLayoutManager(this@FeedDetailActivity)
            adapter = subMenuAdapter
        }
    }

    private fun setupLevel() {
        with(binding.rvLevel) {
            layoutManager = LinearLayoutManager(this@FeedDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = levelAdapter
        }
    }

    private fun setupCommentRecycler() {
        with(binding.rvComment) {
            layoutManager = LinearLayoutManager(this@FeedDetailActivity)
            adapter = commentAdapter
        }
    }

    companion object {
        private const val KEY_FEED_ID = "KEY_FEED_ID"
        fun getIntent(context: Context, feedId: Long) = Intent(context, FeedDetailActivity::class.java)
            .apply {
                putExtra(KEY_FEED_ID, feedId)
            }
    }
}