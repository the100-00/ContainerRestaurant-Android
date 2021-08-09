package container.restaurant.android.data.remote

import com.skydoves.sandwich.ApiResponse
import container.restaurant.android.data.request.SignInWithAccessTokenRequest
import container.restaurant.android.data.request.SignUpWithAccessTokenRequest
import container.restaurant.android.data.request.UpdateProfileRequest
import container.restaurant.android.data.response.NicknameDuplicationCheckResponse
import container.restaurant.android.data.response.ProfileResponse
import container.restaurant.android.data.response.SignUpWithAccessTokenResponse
import retrofit2.http.*

interface AuthService {
    @POST("/api/user/login")
    suspend fun signInWithAccessToken(
        @Body signInWithAccessTokenRequest: SignInWithAccessTokenRequest
    ): ApiResponse<ProfileResponse>

    @GET("/api/user/nickname/exists")
    suspend fun nicknameDuplicationCheck(
        @Query("nickname") nickname: String
    ): ApiResponse<NicknameDuplicationCheckResponse>

    @POST("/api/user")
    suspend fun signUpWithAccessToken(
        @Body signUpWithAccessTokenRequest: SignUpWithAccessTokenRequest
    ): ApiResponse<SignUpWithAccessTokenResponse>

    @PATCH("/api/user/{userId}")
    suspend fun updateProfile(
        @Path("userId") userId: Int,
        @Body updateProfileRequest: UpdateProfileRequest? = null
    ): ApiResponse<ProfileResponse>
}