package container.restaurant.android.data.remote

import com.skydoves.sandwich.ApiResponse
import container.restaurant.android.data.request.signInWithAccessTokenRequest
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/user/login")
    suspend fun signInWithAccessToken(
        @Body signInWithAccessTokenRequest: signInWithAccessTokenRequest
    ): ApiResponse<ResponseBody>
}