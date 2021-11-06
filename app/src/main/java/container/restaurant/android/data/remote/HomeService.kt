package container.restaurant.android.data.remote

import com.skydoves.sandwich.ApiResponse
import container.restaurant.android.data.request.SignInWithAccessTokenRequest
import container.restaurant.android.data.response.*
import retrofit2.http.*

interface HomeService {
    @POST("/api/user/login")
    suspend fun signInWithAccessToken(
        @Body signInWithAccessTokenRequest: SignInWithAccessTokenRequest
    ): ApiResponse<ProfileResponse>

    @GET("/api/home")
    suspend fun homeInfo(@Header("Authorization") tokenBearer: String) : ApiResponse<HomeInfoResponse>

    @GET("/api/feed/recommend")
    suspend fun recommendedFeedList() : ApiResponse<FeedListResponse>

    @GET("/api/feed/user/{userId}")
    suspend fun userFeedList(
        @Path("userId") userId: Int
    ) : ApiResponse<FeedListResponse>

    @GET("/api/user/{userId}")
    suspend fun userInfo(
        @Path("userId") userId: Int
    ) : ApiResponse<UserInfoResponse>
}