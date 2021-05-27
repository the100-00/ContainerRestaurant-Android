package container.restaurant.android.data.repository

import com.tak8997.github.domain.ResultState
import container.restaurant.android.data.model.FeedModel
import container.restaurant.android.data.model.FeedResponse
import container.restaurant.android.data.remote.FeedService
import container.restaurant.android.data.safeApiCall
import container.restaurant.android.presentation.feed.category.FeedCategory

interface FeedRepository {
    suspend fun fetchFeed(category: String, page: Int): ResultState<FeedResponse>
}

private const val perPage = 20

internal class FeedDataRepository(
    private val feedService: FeedService
) : FeedRepository {

    override suspend fun fetchFeed(category: String, page: Int): ResultState<FeedResponse> {
        var feedCategory: String? = category
        if (category == FeedCategory.ALL.name) {
            feedCategory = null
        }
        return safeApiCall { feedService.fetchFeed(feedCategory, page = page, perPage = perPage) }
    }
}
