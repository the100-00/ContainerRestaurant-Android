package container.restaurant.android.presentation.feed.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.tak8997.github.domain.ResultState
import container.restaurant.android.data.model.FeedResponse
import container.restaurant.android.data.repository.FeedRepository
import container.restaurant.android.presentation.feed.item.FeedSort
import container.restaurant.android.util.SingleLiveEvent
import kotlinx.coroutines.launch

internal class FeedCategoryViewModel(
    private val repository: FeedRepository,
    private val feedCategory: FeedCategory?
) : ViewModel() {

    private val feedResponse = MutableLiveData<FeedResponse>()
    private var feedSort: FeedSort = FeedSort.LATEST

    val feeds = feedResponse.map {
        it.feedModel?.feeds ?: emptyList()
    }
    val lastPage = feedResponse.map {
        it.page.totalPage
    }
    val isRefreshing = MutableLiveData<Boolean>()
    val errorToast = SingleLiveEvent<String>()
    val loading = MutableLiveData<Boolean>()

    init {
        fetchFeedsByCategory(FeedCategoryFragment.page)
    }

    fun onClickSort(sort: FeedSort) {
        this.feedSort = sort
    }

    fun onRefresh() {
        isRefreshing.value = false
    }

    fun fetchMore(page: Int) {
        fetchFeedsByCategory(page)
    }

    private fun fetchFeedsByCategory(page: Int) {
        viewModelScope.launch {
            loading.value = true
            val feedsResult = repository.fetchFeedsWithCategory(getFeedCategory(), feedSort, page)
            when (feedsResult) {
                is ResultState.Success -> feedResponse.value = feedsResult.data ?: return@launch
                is ResultState.Error -> errorToast.value = feedsResult.error?.errorMessage ?: ""
            }
            loading.value = false
        }
    }

    private fun getFeedCategory() = feedCategory?.name ?: ""
}