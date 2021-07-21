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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        binding.viewModel = viewModel

        observeData()
        getUserData()
    }

    private fun observeData() {
        with(viewModel){
            observe(isBackButtonClicked){
                finish()
            }
        }
    }

    private fun getUserData() {
        if(viewModel.isUserSignIn()){
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