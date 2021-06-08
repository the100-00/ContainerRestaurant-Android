package container.restaurant.android.data.repository

import com.tak8997.github.domain.ResultState
import container.restaurant.android.data.model.FeedResponse
import container.restaurant.android.data.remote.FeedService
import container.restaurant.android.data.safeApiCall
import container.restaurant.android.presentation.feed.category.FeedCategory
import container.restaurant.android.presentation.feed.item.FeedSort

interface FeedRepository {
    suspend fun fetchFeedsWithCategory(category: String, feedSort: FeedSort, page: Int): ResultState<FeedResponse>
}

private const val perPage = 20

internal class FeedDataRepository(
    private val feedService: FeedService
) : FeedRepository {

    private var feedCategory: String? = null

    override suspend fun fetchFeedsWithCategory(category: String, feedSort: FeedSort, page: Int): ResultState<FeedResponse> {
        feedCategory = category
        if (category == FeedCategory.ALL.name) {
            feedCategory = null
        }

        return safeApiCall { feedService.fetchFeeds(feedCategory, feedSort.sort, page, perPage) }
    }
}
