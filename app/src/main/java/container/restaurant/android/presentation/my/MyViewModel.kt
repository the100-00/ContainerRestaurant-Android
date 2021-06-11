package container.restaurant.android.presentation.my

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import container.restaurant.android.data.repository.MyRepository
import kotlinx.coroutines.flow.MutableStateFlow

class MyViewModel (private val myRepository: MyRepository) : ViewModel() {
    val getErrorMsg =  MutableLiveData<String>()
    val viewLoading = MutableLiveData<Boolean>()

    val loginChk = MutableStateFlow(false)

    fun tempLogin() = myRepository.tempLogin (
        onStart = { viewLoading.postValue(true) },
        onComplete = {viewLoading.postValue(false); loginChk.value = true},
        onError = { getErrorMsg.postValue(it) }
    ).asLiveData()

    fun getUser() = myRepository.getMyUser(
        onStart = { viewLoading.postValue(true) },
        onComplete = {viewLoading.postValue(false) },
        onError = { getErrorMsg.postValue(it) }
    ).asLiveData()

    fun updateProfile(id: Int, nickname: String, profile: String) = myRepository.updateProfile(
        id = id,
        nickname = nickname,
        profile = profile,
        onStart = { viewLoading.postValue(true) },
        onComplete = { viewLoading.postValue(false) },
        onError = { getErrorMsg.postValue(it) }
    ).asLiveData()

    fun getMyFeed(id: Int) = myRepository.getMyFeed(
        id = id,
        onStart = { viewLoading.postValue(true) },
        onComplete = { viewLoading.postValue(false) },
        onError = { getErrorMsg.postValue(it) }
    ).asLiveData()

    fun getMyScrapFeed(id: Int) = myRepository.getMyScrapFeed(
        id = id,
        onStart = { viewLoading.postValue(true) },
        onComplete = { viewLoading.postValue(false) },
        onError = { getErrorMsg.postValue(it) }
    ).asLiveData()

    fun getMyFavorite() = myRepository.getMyFavorite(
        onStart = { viewLoading.postValue(true) },
        onComplete = { viewLoading.postValue(false) },
        onError = { getErrorMsg.postValue(it) }
    ).asLiveData()
}