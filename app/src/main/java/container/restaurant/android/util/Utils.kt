package container.restaurant.android.util

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

fun kakaoLogin(context: Context, callback:(OAuthToken?, Throwable?) -> Unit) {
    val kakaoUserApi = UserApiClient.instance
    if (kakaoUserApi.isKakaoTalkLoginAvailable(context)) {
        kakaoUserApi.loginWithKakaoTalk(context, callback = callback)
    } else {
        kakaoUserApi.loginWithKakaoAccount(context, callback = callback)
    }
}