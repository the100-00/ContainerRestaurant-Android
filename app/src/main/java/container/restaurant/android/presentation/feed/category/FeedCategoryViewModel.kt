package container.restaurant.android.presentation.feed.category

import androidx.lifecycle.*
import com.tak8997.github.domain.ResultState
import container.restaurant.android.data.FeedCategory
import container.restaurant.android.data.SortingCategory
import container.restaurant.android.data.repository.FeedExploreRepository
import container.restaurant.android.data.response.FeedListResponse
import container.restaurant.android.data.response.FeedResponse
import container.restaurant.android.util.SingleLiveEvent
import container.restaurant.android.util.handleApiResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

internal class FeedCategoryViewModel(
    private val feedExploreRepository: FeedExploreRepository,
    private val feedCategory: FeedCategory
) : ViewModel() {

    private val _feedList: MutableLiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>> =
        MutableLiveData()
    val feedList: LiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>> = _feedList

    suspend fun getFeedList(sortingCategory: SortingCategory) {
        val categoryName =
            if (feedCategory.name == FeedCategory.ALL.name) null
            else feedCategory.name
        feedExploreRepository.getFeedList(categoryName, sortingCategory, 0)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _feedList.value = it.data?.embedded?.feedPreviewDtoList
                        Timber.d("feedList.value = ${feedList.value}")
                    }
                )
            }
    }

    /** 여기부터는 원래 있던 코드 **/
//    private val feedResponse = MutableLiveData<FeedResponse>()
//    private var feedSort: SortingCategory = SortingCategory.LATEST
//
//    val feeds = feedResponse.map {
//        it.feedModel?.feeds ?: emptyList()
//    }
//    val lastPage = feedResponse.map {
//        it.page.totalPage
//    }
//    val isRefreshing = MutableLiveData<Boolean>()
//    val errorToast = SingleLiveEvent<String>()
//    val loading = MutableLiveData<Boolean>()
//
//    init {
//        fetchFeedsByCategory(FeedCategoryFragment.page)
//    }
//
//    fun onRefresh() {
//        isRefreshing.value = false
//    }
//
//    fun fetchMore(page: Int) {
//        fetchFeedsByCategory(page)
//    }
//
//    private fun fetchFeedsByCategory(page: Int) {
//        viewModelScope.launch {
//            loading.value = true
//            val feedsResult =
//                feedExploreRepository.fetchFeedsWithCategory(getFeedCategory(), feedSort, page)
//            when (feedsResult) {
//                is ResultState.Success -> feedResponse.value = feedsResult.data ?: return@launch
//                is ResultState.Error -> errorToast.value = feedsResult.error?.errorMessage ?: ""
//            }
//            loading.value = false
//        }
//    }
//
//    private fun getFeedCategory() = feedCategory?.name ?: ""
}