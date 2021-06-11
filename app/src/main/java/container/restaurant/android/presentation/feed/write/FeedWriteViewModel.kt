package container.restaurant.android.presentation.feed.write

import androidx.lifecycle.*
import container.restaurant.android.data.db.MainFood
import container.restaurant.android.data.db.SideDish
import container.restaurant.android.data.repository.FeedWriteRepository
import container.restaurant.android.data.request.FeedWriteRequest
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

class FeedWriteViewModel(private val feedWriteRepository: FeedWriteRepository) : ViewModel() {
    val getErrorMsg =  MutableLiveData<String>()
    val viewLoading = MutableLiveData<Boolean>()

    val mainFoodList = feedWriteRepository.getMainFoodList().asLiveData()
    val sideDishList = feedWriteRepository.getSideDishList().asLiveData()

    fun addMainFood(list: List<MainFood>) = feedWriteRepository.addMainFood(list).asLiveData()
    fun addSideDish(list: List<SideDish>) = feedWriteRepository.addSideDish(list).asLiveData()

    val searchProgressChk = MutableStateFlow(false)
    fun getSearchPlace(place: String) = feedWriteRepository.getSearchPlace(
        place = place,
        onStart = { searchProgressChk.value = true },
        onComplete = { searchProgressChk.value = false },
        onError = {getErrorMsg.postValue(it)}

    ).asLiveData()

    val loginChk = MutableStateFlow(false)

    fun tempLogin() = feedWriteRepository.tempLogin (
        onStart = { viewLoading.postValue(true) },
        onComplete = {viewLoading.postValue(false); loginChk.value = true},
        onError = { getErrorMsg.postValue(it) }
    ).asLiveData()

    fun updateFeed(feedWriteRequest: FeedWriteRequest) = feedWriteRepository.updateFeed(
        feedWriteRequest= feedWriteRequest,
        onStart = { viewLoading.postValue(true) },
        onComplete = { viewLoading.postValue(false) },
        onError = {getErrorMsg.postValue(it)}
    ).asLiveData()

    fun uploadImg(bmpFile: File) = feedWriteRepository.uploadImg(
        bmpFile = bmpFile,
        onStart = {viewLoading.postValue(true)},
        onComplete = {viewLoading.postValue(false)},
        onError = {getErrorMsg.postValue(it)}
    ).asLiveData()
}