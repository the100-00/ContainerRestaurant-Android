package container.restaurant.android.presentation.map.item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tak8997.github.domain.ResultState
import container.restaurant.android.data.response.RestaurantNearInfoDto
import container.restaurant.android.data.response.RestaurantResponse
import container.restaurant.android.data.repository.RestaurantRepository
import container.restaurant.android.util.SingleLiveEvent
import kotlinx.coroutines.launch

internal class MapsViewModel(
    private val resRepository: RestaurantRepository
    ) : ViewModel() {

    val resResponse = MutableLiveData<RestaurantResponse?>()
    val resInfoResponse = MutableLiveData<RestaurantNearInfoDto>()

    val errorToast = SingleLiveEvent<String>()
    val isRefreshing = MutableLiveData<Boolean>()

    fun onRefresh() {
        isRefreshing.value = false
    }

    fun fetchRes(lat : Double, lon : Double, radius : Int){
        fetchRestaurantList(lat, lon, radius)
    }

    private fun fetchRestaurantList(lat : Double, lon : Double, radius : Int){
        viewModelScope.launch {
         val resResult = resRepository.fetchResList( lat, lon,radius)

            when(resResult){
                is ResultState.Success -> {
                    resResponse.value = resResult.data ?: return@launch
                }
                is ResultState.Error -> {
                    resResponse.value = null
                    errorToast.value = resResult.error?.errorMessage ?: ""
                }
            }

        }
    }

    fun fetchRestaurantInfo(id : Long){
        viewModelScope.launch {
            val resResult = resRepository.fetchResInfo(id)

            when(resResult){
                is ResultState.Success -> {
                    resInfoResponse.value = resResult.data ?: return@launch
                }
                is ResultState.Error -> {
                    errorToast.value = resResult.error?.errorMessage ?: ""
                }
            }
        }
    }
}