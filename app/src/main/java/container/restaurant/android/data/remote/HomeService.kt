package container.restaurant.android.data.remote

import com.skydoves.sandwich.ApiResponse
import container.restaurant.android.data.request.SignInWithAccessTokenRequest
import container.restaurant.android.data.response.BannersInfoResponse
import container.restaurant.android.data.response.FeedListResponse
import container.restaurant.android.data.response.ProfileResponse
import container.restaurant.android.data.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeService {
    @POST("/api/user/login")
    suspend fun signInWithAccessToken(
        @Body signInWithAccessTokenRequest: SignInWithAccessTokenRequest
    ): ApiResponse<ProfileResponse>

    @GET("/banners")
    suspend fun bannersInfo() : ApiResponse<BannersInfoResponse>

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