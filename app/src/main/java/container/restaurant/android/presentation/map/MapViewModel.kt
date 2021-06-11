package container.restaurant.android.presentation.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    val isRefreshing = MutableLiveData<Boolean>()

    fun onRefresh() {

        isRefreshing.value = false
    }

    fun fetchMore() {

    }
}