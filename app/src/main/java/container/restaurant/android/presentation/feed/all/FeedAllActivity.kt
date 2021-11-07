package container.restaurant.android.presentation.feed.all

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivityFeedAllBinding
import container.restaurant.android.presentation.user.UserProfileActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FeedAllActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedAllBinding

    private val viewModel: FeedAllViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityFeedAllBinding>(this, R.layout.activity_feed_all)
            .apply {
                lifecycleOwner = this@FeedAllActivity
                viewModel = this@FeedAllActivity.viewModel
            }

        observeData()
        initData()
    }

    private fun observeData() {

        with(viewModel) {
            isCloseClicked.observe(this@FeedAllActivity) {
                finish()
            }
            isHelpClicked.observe(this@FeedAllActivity) {
                FeedAllHelpDialogFragment().show(supportFragmentManager, "FeedAllHelpDialogFragment")
            }
            navToUserProfile.observe(this@FeedAllActivity) {
                startActivity(UserProfileActivity.getIntent(this@FeedAllActivity))
            }
        }

    }

    private fun initData() {
        lifecycleScope.launchWhenCreated {
            viewModel.getAllContainer()
        }
    }


    companion object {
        fun getIntent(context: Context) = Intent(context, FeedAllActivity::class.java)
    }
}