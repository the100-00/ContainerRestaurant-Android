package container.restaurant.android.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivitySignInBinding
import container.restaurant.android.presentation.MainViewModel
import container.restaurant.android.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SignInActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModel()

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
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignInFragment.newInstance())
                .commit()
        }
    }
}