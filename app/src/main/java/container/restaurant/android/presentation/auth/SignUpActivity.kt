package container.restaurant.android.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivitySignUpBinding
import container.restaurant.android.presentation.base.BaseActivity
import container.restaurant.android.util.DataTransfer
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SignUpActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModel()

    private val provider: String? by lazy {
        intent.getStringExtra(DataTransfer.PROVIDER)
    }
    private val accessToken: String? by lazy {
        intent.getStringExtra(DataTransfer.ACCESS_TOKEN)
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, SignUpActivity::class.java)
            .apply { }
    }

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivitySignUpBinding>(this, R.layout.activity_sign_up)
                .apply {
                    lifecycleOwner = this@SignUpActivity
                    viewModel = this@SignUpActivity.viewModel
                }

        if (savedInstanceState == null) {
            replaceSignUpFragment()
        }

    }

    private fun replaceSignUpFragment() {
        val signUpFragment = SignUpFragment.newInstance()
        val bundle = Bundle()
        bundle.putString(DataTransfer.PROVIDER, provider)
        bundle.putString(DataTransfer.ACCESS_TOKEN, accessToken)
        signUpFragment.arguments = bundle
        supportFragmentManager.commit {
            replace(R.id.container, signUpFragment)
        }
    }
}