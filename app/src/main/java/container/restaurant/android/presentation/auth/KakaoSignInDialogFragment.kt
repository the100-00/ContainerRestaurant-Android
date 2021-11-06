package container.restaurant.android.presentation.auth

import android.content.DialogInterface
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
import container.restaurant.android.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


internal class KakaoSignInDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentKakaoSigninBinding
    val viewModel: AuthViewModel by viewModel()

    private lateinit var provider: String
    private lateinit var kakaoAccessToken: String

    private var onCloseListener: OnCloseListener? = null

    fun setOnCloseListener(onCloseListener: OnCloseListener) {
        this.onCloseListener = onCloseListener
    }

    private val kakaoUserApi by lazy {
        UserApiClient.instance
    }

    private val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, err ->
        if (err != null) {
            Timber.e(err, "카카오 인증 실패")
            dismiss()
        } else if (token != null) {
            kakaoUserApi.me { userKakao, err2 ->
                if (err2 != null) {
                    Timber.e(err2, "카카오 사용자 정보 요청 실패")
                    dismiss()
                } else if (userKakao != null) {
                    Timber.d("카카오 인증 성공")
                    provider = Provider.KAKAO.providerStr
                    kakaoAccessToken = token.accessToken
                    lifecycleScope.launchWhenCreated {
                        viewModel.generateAccessToken(
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
            errorMessageId.observe(this@KakaoSignInDialogFragment, EventObserver {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_LONG).show()
                this@KakaoSignInDialogFragment.dismiss()
            })
        }
    }

    //다이얼로그 바깥 쪽 터치 시 호출됨
    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCloseListener?.onClose()
    }

    fun kakaoLogin() {
        if (kakaoUserApi.isKakaoTalkLoginAvailable(requireContext())) {
            kakaoUserApi.loginWithKakaoTalk(requireContext(), callback = kakaoCallback)
        } else {
            kakaoUserApi.loginWithKakaoAccount(requireContext(), callback = kakaoCallback)
        }
    }

    private fun setOnClickListeners() {
        binding.tvKakaoLogin.setOnClickListener {
            kakaoLogin()
        }

        //dismiss 는 cancel 과 다른 것 확인함!
        binding.imgClose.setOnClickListener {
            onCloseListener?.onClose()
            dismissAllowingStateLoss()
        }
    }
}