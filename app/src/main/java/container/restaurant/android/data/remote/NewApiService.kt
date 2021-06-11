package container.restaurant.android.data.remote

import com.skydoves.sandwich.ApiResponse
import container.restaurant.android.data.request.FeedWriteRequest
import container.restaurant.android.data.request.UpdateUserRequest
import container.restaurant.android.data.response.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface NewApiService {
    @GET("/api/user/temp-login")
    suspend fun getTempLogin(): ApiResponse<ResponseBody>

    @Headers("accept: application/json", "content-type: application/json")
    @POST("/api/feed")
    suspend fun updateFeed(@Header("Cookie") cookie: String, @Body params: FeedWriteRequest) : ApiResponse<ResponseBody>

    @Multipart
    @POST("/api/image/upload")
    suspend fun uploadImage(@Part image: MultipartBody.Part): ApiResponse<ImageUploadResponse>

    @GET("/api/user")
    suspend fun getUser(@Header("Cookie") cookie: String) : ApiResponse<UserResponse>

    @Headers("accept: application/json", "content-type: application/json")
    @PATCH("/api/user/{id}")
    suspend fun updateProfile(@Header("Cookie") cookie: String, @Path("id")id: Int, @Body params: UpdateUserRequest) : ApiResponse<UpdateProfileResponse>

    @GET("/api/feed/user/{id}")
    suspend fun getMyFeed(@Header("Cookie") cookie: String, @Path("id")id: Int) : ApiResponse<MyFeedResponse>

    @GET("/api/feed/user/{id}/scrap")
    suspend fun getMyScrapFeed(@Header("Cookie") cookie: String, @Path("id")id: Int) : ApiResponse<MyFeedResponse>

    @GET("/api/favorite/restaurant")
    suspend fun getFavoriteRestaurant(@Header("Cookie") cookie: String) : ApiResponse<MyFavoriteResponse>
}