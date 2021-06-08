package container.restaurant.android.data.remote

import container.restaurant.android.data.model.FeedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedService {

    @GET("api/feed")
    suspend fun fetchFeeds(
        @Query("category") category: String? = null,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int,
        @Query("size") perPage: Int
    ): FeedResponse
}