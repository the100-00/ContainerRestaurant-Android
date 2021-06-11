package container.restaurant.android.presentation.map.item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tak8997.github.domain.ResultState
import container.restaurant.android.data.model.FeedResponse
import container.restaurant.android.data.repository.FeedRepository
import container.restaurant.android.util.SingleLiveEvent
import kotlinx.coroutines.launch

internal class FeedRestaurantViewModel(
    private val feedRepository: FeedRepository
) : ViewModel() {

    val feedResponse = MutableLiveData<FeedResponse>()

    val isRefreshing = MutableLiveData<Boolean>()
    val errorToast = SingleLiveEvent<String>()
    val loading = MutableLiveData<Boolean>()

    fun fetchResFeed(id : Long){
        viewModelScope.launch {
            val resResult = feedRepository.fetchResFeed(id)
            when(resResult){
                is ResultState.Success -> {
                    feedResponse.value = resResult.data ?: return@launch
                }
                is ResultState.Error -> {
                    errorToast.value = resResult.error?.errorMessage ?: ""
                }
            }
        }
    }

    fun onRefresh() {
        isRefreshing.value = false
    }
}