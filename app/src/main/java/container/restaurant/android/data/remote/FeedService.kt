package container.restaurant.android.data.remote

import container.restaurant.android.data.model.*
import retrofit2.http.*

interface FeedService {

    @GET("api/feed")
    suspend fun fetchFeed(
        @Query("category") category: String? = null,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int,
        @Query("size") perPage: Int
    ): FeedResponse

    @GET("api/feed/{id}")
    suspend fun fetchFeedDetail(@Path("id") feedId: Long): FeedDetail

    @GET("api/comment/feed/{feedId}")
    suspend fun fetchFeedComment(@Path("feedId") feedId: Long): FeedCommentResponse

    @Headers(
        "Content-Type: application/json;charset=UTF-8",
        "Accept: */*",
        "Accept-Encoding: gzip, deflate, br"
    )
    @POST("api/comment/{feedId}")
    suspend fun postFeedComment(
        @Path("feedId") feedId: Long,
        @Body postComment: PostComment
    ): FeedComment

    @Headers(
        "Content-Type: application/json;charset=UTF-8",
        "Accept: */*",
        "Accept-Encoding: gzip, deflate, br"
    )
    @POST("api/like/feed/{id}")
    suspend fun postFeedLike(@Path("id") feedId: Long): FeedDummyResponse

    @Headers(
        "Content-Type: application/json;charset=UTF-8",
        "Accept: */*",
        "Accept-Encoding: gzip, deflate, br"
    )
    @DELETE("api/like/feed/{id}")
    suspend fun removeFeedLike(@Path("id") feedId: Long): FeedDummyResponse

    @Headers(
        "Content-Type: application/json;charset=UTF-8",
        "Accept: */*",
        "Accept-Encoding: gzip, deflate, br"
    )
    @POST("api/scrap/{id}")
    suspend fun postFeedScrap(@Path("id") feedId: Long): FeedDummyResponse

    @Headers(
        "Content-Type: application/json;charset=UTF-8",
        "Accept: */*",
        "Accept-Encoding: gzip, deflate, br"
    )
    @DELETE("api/scrap/{id}")
    suspend fun removeFeedScrap(@Path("id") feedId: Long): FeedDummyResponse
}