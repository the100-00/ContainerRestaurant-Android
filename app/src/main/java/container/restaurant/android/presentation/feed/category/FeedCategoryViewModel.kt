package container.restaurant.android.presentation.feed.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class FeedCategoryViewModel : ViewModel() {

    val isRefreshing = MutableLiveData<Boolean>()

    fun onRefresh() {

        isRefreshing.value = false
    }

    fun fetchMore() {

    }
}