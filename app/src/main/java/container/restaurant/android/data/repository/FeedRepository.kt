package container.restaurant.android.data.repository

import com.tak8997.github.domain.ResultState
import container.restaurant.android.data.SortingCategory
import container.restaurant.android.data.remote.FeedService
import container.restaurant.android.data.response.FeedResponse
import container.restaurant.android.data.safeApiCall
import container.restaurant.android.presentation.feed.category.FeedCategory

interface FeedRepository {
    suspend fun fetchFeedsWithCategory(category: String, sortingCategory: SortingCategory, page: Int): ResultState<FeedResponse>
    suspend fun fetchResFeed(resId: Long): ResultState<FeedResponse>
}

private const val perPage = 20

internal class FeedDataRepository(
    private val feedService: FeedService
) : FeedRepository {

    private var feedCategory: String? = null

    override suspend fun fetchFeedsWithCategory(category: String, sortingCategory: SortingCategory, page: Int): ResultState<FeedResponse> {
        feedCategory = category
        if (category == FeedCategory.ALL.name) {
            feedCategory = null
        }

        return safeApiCall { feedService.fetchFeeds(feedCategory, sortingCategory.sort, page, perPage) }
    }

    override suspend fun fetchResFeed(resId: Long): ResultState<FeedResponse> {
        return safeApiCall { feedService.fetchResFeed(resId) }
    }
}
