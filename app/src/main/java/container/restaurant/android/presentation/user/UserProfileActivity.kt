package container.restaurant.android.presentation.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivityUserProfileBinding
import container.restaurant.android.presentation.feed.item.ContainerFeedAdapter

internal class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    private val containerFeedAdapter by lazy {
        ContainerFeedAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)

        setupFeedRecycler()
    }

    private fun setupFeedRecycler() {
        with(binding.rvContainerFeed) {
            layoutManager = GridLayoutManager(this@UserProfileActivity, 2)
            adapter = containerFeedAdapter
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, UserProfileActivity::class.java)
    }
}