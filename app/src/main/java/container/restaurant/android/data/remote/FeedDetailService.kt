package container.restaurant.android.data.remote

import com.skydoves.sandwich.ApiResponse
import container.restaurant.android.data.response.FeedDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FeedDetailService {
    @GET("api/feed/{feed_id}")
    suspend fun feedDetail(@Path("feed_id") feedId: Int): ApiResponse<FeedDetailResponse>
}