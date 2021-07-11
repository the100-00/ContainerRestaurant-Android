package container.restaurant.android.data.remote

import com.skydoves.sandwich.ApiResponse
import container.restaurant.android.data.request.SignInWithAccessTokenRequest
import container.restaurant.android.data.response.SignInWithAccessTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeService {
    @POST("/api/user/login")
    suspend fun signInWithAccessToken(
        @Body signInWithAccessTokenRequest: SignInWithAccessTokenRequest
    ): ApiResponse<SignInWithAccessTokenResponse>
}