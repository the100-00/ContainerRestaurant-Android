package container.restaurant.android.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentKakaoSigninBinding
import container.restaurant.android.presentation.user.UserProfileActivity
import container.restaurant.android.util.DataTransfer
import container.restaurant.android.util.EventObserver
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


internal class KakaoSignInDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentKakaoSigninBinding
    private val viewModel: AuthViewModel by sharedViewModel()

    private lateinit var provider: String
    private lateinit var accessToken: String

    private val kakaoUserApi by lazy {
        UserApiClient.instance
    }

    enum class Provider(val providerStr: String) {
        KAKAO("KAKAO")
    }

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, err ->
        if (err != null) {
            Timber.e(err, "카카오 인증 실패")
        } else if (token != null) {
            kakaoUserApi.me { userKakao, err2 ->
                if (err2 != null) {
                    Timber.e(err2, "카카오 사용자 정보 요청 실패")
                } else if (userKakao != null) {
                    Timber.d("카카오 인증 성공")
                    provider = Provider.KAKAO.providerStr
                    accessToken = token.accessToken
                    lifecycleScope.launchWhenCreated {
                        viewModel.signInWithAccessToken(
                            Provider.KAKAO.providerStr,
                            token.accessToken
                        )
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKakaoSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        with(viewModel) {
            observe(signInWithAccessTokenResult) { response ->
                storeUserId(response.id)
            }
            signInWithAccessTokenSuccess.observe(this@KakaoSignInDialogFragment, EventObserver {
                startActivity(UserProfileActivity.getIntent(requireContext()))
                this@KakaoSignInDialogFragment.dismiss()
            })
            notOurUser.observe(this@KakaoSignInDialogFragment, EventObserver {
                val intent = SignUpActivity.getIntent(requireContext()).apply {
                    putExtra(DataTransfer.PROVIDER, provider)
                    putExtra(DataTransfer.ACCESS_TOKEN, accessToken)
                }
                startActivity(intent)
                this@KakaoSignInDialogFragment.dismiss()
            })
            errorMessageId.observe(this@KakaoSignInDialogFragment, EventObserver {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_LONG).show()
                this@KakaoSignInDialogFragment.dismiss()
            })
        }

    }

    private fun setOnClickListeners() {
        binding.tvKakaoLogin.setOnClickListener {
            if (kakaoUserApi.isKakaoTalkLoginAvailable(requireContext())) {
                kakaoUserApi.loginWithKakaoTalk(requireContext(), callback = callback)
            } else {
                kakaoUserApi.loginWithKakaoAccount(requireContext(), callback = callback)
            }
        }
        binding.imgClose.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }
}