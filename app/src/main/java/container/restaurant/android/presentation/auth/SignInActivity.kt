package container.restaurant.android.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivitySignInBinding

internal class SignInActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, SignInActivity::class.java)
            .apply {  }
    }

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignInFragment.newInstance())
                .commit()
        }
    }
}