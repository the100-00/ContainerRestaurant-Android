package container.restaurant.android.presentation.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivityUserProfileBinding
import container.restaurant.android.presentation.home.HomeViewModel
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityUserProfileBinding>(
            this,
            R.layout.activity_user_profile
        )
            .apply {
                viewModel = this@UserProfileActivity.viewModel
                lifecycleOwner = this@UserProfileActivity
            }
        observeData()
        getUserData()
    }

    private fun observeData() {
        with(viewModel) {
            observe(isBackButtonClicked) {
                finish()
            }
            observe(userLevelTitle) { userLevelTitle ->
                if (userProfileUrl.value == null) {
                    when (userLevelTitle) {
                        getString(R.string.empty_profile_lv1) -> userProfileRes.value =
                            R.drawable.empty_profile_lv1
                        getString(R.string.empty_profile_lv2) -> userProfileRes.value =
                            R.drawable.empty_profile_lv2
                        getString(R.string.empty_profile_lv3) -> userProfileRes.value =
                            R.drawable.empty_profile_lv3
                        getString(R.string.empty_profile_lv4) -> userProfileRes.value =
                            R.drawable.empty_profile_lv4
                        getString(R.string.empty_profile_lv5) -> userProfileRes.value =
                            R.drawable.empty_profile_lv5
                    }
                }

            }
        }
    }

    private fun getUserData() {
        if (viewModel.isUserSignIn()) {
            lifecycleScope.launchWhenCreated {
                viewModel.getUserFeedList()
                viewModel.getUserInfo()
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, UserProfileActivity::class.java)
    }
}