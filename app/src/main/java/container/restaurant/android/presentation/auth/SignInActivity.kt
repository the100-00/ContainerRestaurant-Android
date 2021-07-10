package container.restaurant.android.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivitySignInBinding
import container.restaurant.android.presentation.MainViewModel
import container.restaurant.android.presentation.base.BaseActivity
import container.restaurant.android.util.DataTransfer
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

internal class SignInActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModel()

    private val provider: String? by lazy {
        intent.getStringExtra(DataTransfer.PROVIDER)
    }
    private val accessToken: String? by lazy{
        intent.getStringExtra(DataTransfer.ACCESS_TOKEN)
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, SignInActivity::class.java)
            .apply { }
    }

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySignInBinding>(this, R.layout.activity_sign_in)
                .apply {
                    lifecycleOwner = this@SignInActivity
                    viewModel = this@SignInActivity.viewModel
                }
        observe(viewModel.signInWithAccessTokenResult){

        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignInFragment.newInstance())
                .commit()
        }

        Timber.d("provider : $provider")
        Timber.d("accessToken : $accessToken")
        lifecycleScope.launchWhenCreated {
            provider?.also{ provider ->
                accessToken?.also{ accessToken ->
                    viewModel.signInWithAccessToken(provider,accessToken)
                }
            }
        }


    }
}