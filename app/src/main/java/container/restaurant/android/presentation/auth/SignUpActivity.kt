package container.restaurant.android.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.user.UserApiClient
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivitySignUpBinding
import container.restaurant.android.presentation.base.BaseActivity
import container.restaurant.android.util.DataTransfer
import container.restaurant.android.util.Provider
import container.restaurant.android.util.kakaoLogin
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

internal class SignUpActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModel()

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
        kakaoLogin(this){token, err ->
            if (err != null) {
                Timber.e(err, "카카오 인증 실패")
                Toast.makeText(this, "카카오 인증 실패", Toast.LENGTH_LONG).show()
            } else if (token != null) {
                UserApiClient.instance.me { userKakao, err2 ->
                    if (err2 != null) {
                        Timber.e(err2, "카카오 사용자 정보 요청 실패")
                        Toast.makeText(this, "카카오 사용자 정보 요청 실패", Toast.LENGTH_LONG).show()
                    } else if (userKakao != null) {
                        Timber.d("카카오 인증 성공")
                        val provider = Provider.KAKAO.providerStr
                        val kakaoAccessToken = token.accessToken

                        bundle.putString(DataTransfer.PROVIDER, provider)
                        bundle.putString(DataTransfer.ACCESS_TOKEN, kakaoAccessToken)
                        signUpFragment.arguments = bundle
                        supportFragmentManager.commit {
                            replace(R.id.container, signUpFragment)
                        }
                    }
                }
            }
        }
    }

}