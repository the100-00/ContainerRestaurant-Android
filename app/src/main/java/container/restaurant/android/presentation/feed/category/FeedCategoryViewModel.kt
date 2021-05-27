package container.restaurant.android.presentation.feed.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.tak8997.github.domain.ResultState
import container.restaurant.android.data.model.FeedResponse
import container.restaurant.android.data.repository.FeedRepository
import container.restaurant.android.util.SingleLiveEvent
import kotlinx.coroutines.launch

internal class FeedCategoryViewModel(
    private val repository: FeedRepository,
    private val feedCategory: FeedCategory?
) : ViewModel() {

    private val feedResponse = MutableLiveData<FeedResponse>()

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
        fetchFeedCategory(FeedCategoryFragment.page)
    }

    fun onRefresh() {
        isRefreshing.value = false
    }

    fun fetchMore(page: Int) {
        fetchFeedCategory(page)
    }

    private fun fetchFeedCategory(page: Int) {
        viewModelScope.launch {
            loading.value = true
            val feedResult = repository.fetchFeed(getFeedCategory(), page)
            when (feedResult) {
                is ResultState.Success -> {
                    feedResponse.value = feedResult.data ?: return@launch
                }
                is ResultState.Error -> {
                    errorToast.value = feedResult.error?.errorMessage ?: ""
                }
            }
            loading.value = false
        }
    }

    private fun getFeedCategory() = feedCategory?.name ?: ""
}