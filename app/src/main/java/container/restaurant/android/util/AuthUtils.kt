package container.restaurant.android.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import container.restaurant.android.data.response.UserInfoResponse
import container.restaurant.android.presentation.auth.AuthViewModel
import container.restaurant.android.presentation.auth.KakaoSignInDialogFragment
import container.restaurant.android.presentation.auth.SignUpActivity
import container.restaurant.android.util.Provider
import container.restaurant.android.util.kakaoLogin
import container.restaurant.android.util.observe
import timber.log.Timber

/**
 * 이미 프로젝트에 저장된 토큰, 아이디가 없을 때
 **/
fun observeKakaoFragmentData(
    fragmentActivity: FragmentActivity,
    kakaoSignInDialogFragment: KakaoSignInDialogFragment,
    signUpResultLauncher: ActivityResultLauncher<Intent>
) {
    fragmentActivity.lifecycleScope.launchWhenCreated {
        kakaoSignInDialogFragment.whenCreated {
            with(kakaoSignInDialogFragment.viewModel) {
                isGenerateAccessTokenSuccess.observe(fragmentActivity) {
                    fragmentActivity.lifecycleScope.launchWhenCreated {
                        signInWithAccessToken(
                            onNicknameNull = {
                                signUpResultLauncher.launch(
                                    Intent(
                                        fragmentActivity,
                                        SignUpActivity::class.java
                                    ))
                                kakaoSignInDialogFragment.dismiss()
                            },
                            onSignInSuccess = {
                                kakaoSignInDialogFragment.dismiss()
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * 이미 프로젝트에 저장된 토큰, 아이디가 있을 때
 **/
suspend fun ifAlreadySignIn(
    authViewModel: AuthViewModel,
    fragmentActivity: FragmentActivity
) {
    authViewModel.signInWithAccessToken(
        onInvalidToken = {
            kakaoLogin(fragmentActivity) { token, err ->
                if (err != null) {
                    Timber.e(err, "카카오 인증 실패")
                    Toast.makeText(fragmentActivity, "카카오 인증 실패", Toast.LENGTH_LONG).show()
                } else if (token != null) {
                    UserApiClient.instance.me { userKakao, err2 ->
                        if (err2 != null) {
                            Timber.e(err2, "카카오 사용자 정보 요청 실패")
                            Toast.makeText(
                                fragmentActivity,
                                "카카오 사용자 정보 요청 실패",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (userKakao != null) {
                            Timber.d("카카오 인증 성공")
                            val provider = Provider.KAKAO.providerStr
                            val kakaoAccessToken = token.accessToken
                            fragmentActivity.lifecycleScope.launchWhenCreated {
                                authViewModel.generateAccessToken(
                                    provider,
                                    kakaoAccessToken
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

fun observeAuthViewModelUserInfo(lifecycleOwner: LifecycleOwner, authViewModel: AuthViewModel, onSignInSuccess: (UserInfoResponse) -> Unit) {
    with(authViewModel) {
        userInfoResponse.observe(lifecycleOwner) {
            if(it!=null){
                onSignInSuccess(it)
            }
        }
    }
}

fun kakaoLogin(context: Context, callback:(OAuthToken?, Throwable?) -> Unit) {
    val kakaoUserApi = UserApiClient.instance
    if (kakaoUserApi.isKakaoTalkLoginAvailable(context)) {
        kakaoUserApi.loginWithKakaoTalk(context, callback = callback)
    } else {
        kakaoUserApi.loginWithKakaoAccount(context, callback = callback)
    }
}