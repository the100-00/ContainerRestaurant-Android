package container.restaurant.android.data.remote

import com.skydoves.sandwich.ApiResponse
import container.restaurant.android.data.request.SignInWithAccessTokenRequest
import container.restaurant.android.data.request.SignUpWithAccessTokenRequest
import container.restaurant.android.data.response.NicknameDuplicationCheckResponse
import container.restaurant.android.data.response.SignInWithAccessTokenResponse
import container.restaurant.android.data.response.SignUpWithAccessTokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("/api/user/login")
    suspend fun signInWithAccessToken(
        @Body signInWithAccessTokenRequest: SignInWithAccessTokenRequest
    ): ApiResponse<SignInWithAccessTokenResponse>

    @GET("/api/user/nickname/exists")
    suspend fun nicknameDuplicationCheck(
        @Query("nickname") nickname:String
    ): ApiResponse<NicknameDuplicationCheckResponse>

    @POST("/api/user")
    suspend fun signUpWithAccessToken(
        @Body signUpWithAccessTokenRequest: SignUpWithAccessTokenRequest
    ): ApiResponse<SignUpWithAccessTokenResponse>
}