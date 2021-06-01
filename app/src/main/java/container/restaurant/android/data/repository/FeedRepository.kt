package container.restaurant.android.data.repository

import com.tak8997.github.domain.ResultState
import container.restaurant.android.data.model.*
import container.restaurant.android.data.remote.FeedService
import container.restaurant.android.data.safeApiCall
import container.restaurant.android.presentation.feed.category.FeedCategory

interface FeedRepository {
    suspend fun fetchFeed(category: String, page: Int): ResultState<FeedResponse>
    suspend fun fetchFeedDetail(feedId: Long): ResultState<FeedDetail>
    suspend fun fetchFeedComment(feedId: Long): ResultState<List<FeedComment>>
    suspend fun postFeedComment(feedId: Long, postComment: PostComment): ResultState<FeedComment>
    suspend fun postFeedLike(feedId: Long): ResultState<FeedDummyResponse>
    suspend fun removeFeedLike(feedId: Long): ResultState<FeedDummyResponse>
    suspend fun postFeedScrap(feedId: Long): ResultState<FeedDummyResponse>
    suspend fun removeFeedScrap(feedId: Long): ResultState<FeedDummyResponse>
}

private const val perPage = 20

internal class FeedDataRepository(
    private val feedService: FeedService
) : FeedRepository {

    override suspend fun fetchFeed(category: String, page: Int): ResultState<FeedResponse> {
        var feedCategory: String? = category
        if (category == FeedCategory.DEFAULT.name) {
            feedCategory = null
        }
        return safeApiCall { feedService.fetchFeed(feedCategory, page = page, perPage = perPage) }
    }

    override suspend fun fetchFeedDetail(feedId: Long): ResultState<FeedDetail> {
        return safeApiCall { feedService.fetchFeedDetail(feedId) }
    }

    override suspend fun fetchFeedComment(feedId: Long): ResultState<List<FeedComment>> {
        return safeApiCall { feedService.fetchFeedComment(feedId).data.feedComments }
    }

    override suspend fun postFeedComment(feedId: Long, postComment: PostComment): ResultState<FeedComment> {
        return safeApiCall { feedService.postFeedComment(feedId, postComment) }
    }

    override suspend fun postFeedLike(feedId: Long): ResultState<FeedDummyResponse> {
        return safeApiCall { feedService.postFeedLike(feedId) }
    }

    override suspend fun removeFeedLike(feedId: Long): ResultState<FeedDummyResponse> {
        return safeApiCall { feedService.removeFeedLike(feedId) }
    }

    override suspend fun postFeedScrap(feedId: Long): ResultState<FeedDummyResponse> {
        return safeApiCall { feedService.postFeedScrap(feedId) }
    }

    override suspend fun removeFeedScrap(feedId: Long): ResultState<FeedDummyResponse> {
        return safeApiCall { feedService.removeFeedScrap(feedId) }
    }
}
