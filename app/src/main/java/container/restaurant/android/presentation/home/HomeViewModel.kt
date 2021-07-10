package container.restaurant.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import container.restaurant.android.data.PrefStorage
import container.restaurant.android.data.repository.HomeRepository
import container.restaurant.android.data.response.SignInWithAccessTokenResponse
import container.restaurant.android.util.Event
import container.restaurant.android.util.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import timber.log.Timber

internal class HomeViewModel(
    private val prefStorage: PrefStorage,
    private val homeRepository: HomeRepository
) : ViewModel() {

    val navToAllContainerFeed = MutableLiveData<Event<Unit>>()

    private val _signInWithAccessTokenResult = MutableLiveData<SignInWithAccessTokenResponse>()
    val signInWithAccessTokenResult:LiveData<SignInWithAccessTokenResponse> = _signInWithAccessTokenResult

    private val _signInWithAccessTokenSuccess = MutableLiveData<Event<Boolean>>()
    val signInWithAccessTokenSuccess : LiveData<Event<Boolean>> = _signInWithAccessTokenSuccess

    private val _notOurUser = MutableLiveData<Event<Boolean>>()
    val notOurUser: LiveData<Event<Boolean>> = _notOurUser

    private val _isNavToAllContainerFeedClicked = MutableLiveData<Event<Boolean>>()
    val isNavToAllContainerFeedClicked : LiveData<Event<Boolean>> = _isNavToAllContainerFeedClicked

    private val _isNavToMyContainerFeedClicked = MutableLiveData<Event<Boolean>>()
    val isNavToMyContainerFeedClicked : LiveData<Event<Boolean>> = _isNavToMyContainerFeedClicked

    fun onClickMyContainerFeed() {
        _isNavToMyContainerFeedClicked.value = Event(true)
    }

    fun onClickAllContainerFeed() {
        _isNavToAllContainerFeedClicked.value = Event(true)
    }

    fun isUserSignIn(): Boolean {
        return prefStorage.isUserSignIn
    }


    suspend fun signInWithAccessToken(provider: String, accessToken: String) {
        Timber.d("AuthViewModel signInWithAccessToken called")
        //asLiveData()는 LiveDataScope로 하는 코루틴 블록을 만든다.
        //LiveDataScope는 LiveData가 active한 상태에 있어야 동작한다.
        //그러나 LiveData의 관찰자가 없으면 LiveData는 inActive한 상태에 있고,동작 자체를 하지 않는다.
        homeRepository.signInWithAccessToken(
            provider = provider,
            accessToken = accessToken,
            onStart = { Timber.d("signInWithAccessToken onStart") },
            onComplete = { Timber.d("signInWithAccessToken onComplete") },
            onError = { Timber.d("signInWithAccessToken onError") })
            .collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        Timber.d("response.headers : ${response.headers}")
                        Timber.d("response.raw : ${response.raw}")
                        Timber.d("response.response : ${response.response}")
                        Timber.d("response.statusCode : ${response.statusCode}")
                        Timber.d("response.data : ${response.data}")
                        response.data?.let{
                            _signInWithAccessTokenResult.value = it
                        }
                        _signInWithAccessTokenSuccess.value = Event(true)
                    }
                    is ApiResponse.Failure.Error -> {
                        Timber.d("response.headers : ${response.headers}")
                        Timber.d("response.raw : ${response.raw}")
                        Timber.d("response.response : ${response.response}")
                        Timber.d("response.statusCode : ${response.statusCode}")
                        Timber.d("response.errorBody : ${response.errorBody}")
                        when(response.statusCode) {
                            StatusCode.Unauthorized -> {
                                _notOurUser.value = Event(true)
                            }
                        }
                    }
                    is ApiResponse.Failure.Exception -> {
                        Timber.d("response.message : ${response.message}")
                        Timber.d("response.exception : ${response.exception}")
                    }
                }
            }
    }
}