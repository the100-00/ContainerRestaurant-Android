package container.restaurant.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skydoves.sandwich.ApiResponse
import container.restaurant.android.data.PrefStorage
import container.restaurant.android.data.repository.HomeRepository
import container.restaurant.android.data.response.BannersInfoResponse
import container.restaurant.android.data.response.FeedListResponse
import container.restaurant.android.data.response.SignInWithAccessTokenResponse
import container.restaurant.android.data.response.UserInfoResponse
import container.restaurant.android.util.Event
import container.restaurant.android.util.handleApiResponse
import kotlinx.coroutines.flow.collect
import timber.log.Timber

internal class HomeViewModel(
    private val prefStorage: PrefStorage,
    private val homeRepository: HomeRepository
) : ViewModel() {

    val navToAllContainerFeed = MutableLiveData<Event<Unit>>()

    private val _signInWithAccessTokenResult = MutableLiveData<SignInWithAccessTokenResponse>()
    val signInWithAccessTokenResult: LiveData<SignInWithAccessTokenResponse> = _signInWithAccessTokenResult

    private val _bannerList = MutableLiveData<BannersInfoResponse.BannerInfoDtoList>()
    val bannerList: LiveData<BannersInfoResponse.BannerInfoDtoList> = _bannerList

    private val _recommendedFeedList =
        MutableLiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>>()
    val recommendedFeedList: LiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>> = _recommendedFeedList

    private val _userFeedList = MutableLiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>>()
    val userFeedList: LiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>> = _userFeedList

    private val _userInfo = MutableLiveData<UserInfoResponse>()
    val userInfo: LiveData<UserInfoResponse> = _userInfo

    private val _isNavToAllContainerFeedClicked = MutableLiveData<Event<Boolean>>()
    val isNavToAllContainerFeedClicked: LiveData<Event<Boolean>> = _isNavToAllContainerFeedClicked

    private val _isNavToMyContainerFeedClicked = MutableLiveData<Event<Boolean>>()
    val isNavToMyContainerFeedClicked: LiveData<Event<Boolean>> = _isNavToMyContainerFeedClicked

    fun onClickMyContainerFeed() {
        _isNavToMyContainerFeedClicked.value = Event(true)
    }

    fun onClickAllContainerFeed() {
        _isNavToAllContainerFeedClicked.value = Event(true)
    }

    fun isUserSignIn(): Boolean {
        return prefStorage.isUserSignIn
    }

    suspend fun getBannersInfo() {
        homeRepository.getBannersInfo()
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _bannerList.value = it.data?.embedded
                        Timber.d("it.data : ${it.data}")
                        Timber.d("it.headers : ${it.headers}")
                        Timber.d("it.raw : ${it.raw}")
                        Timber.d("it.response : ${it.response}")
                        Timber.d("it.statusCode : ${it.statusCode}")
                    },
                    onError = {
                        Timber.d("it.errorBody : ${it.errorBody}")
                        Timber.d("it.headers : ${it.headers}")
                        Timber.d("it.raw : ${it.raw}")
                        Timber.d("it.response : ${it.response}")
                        Timber.d("it.statusCode : ${it.statusCode}")
                    },
                    onException = {
                        Timber.d("it.message : ${it.message}")
                        Timber.d("it.exception : ${it.exception}")
                    },
                )
            }
    }

    suspend fun getRecommendedFeedList() {
        homeRepository.getRecommendedFeedList()
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _recommendedFeedList.value = it.data?.embedded?.feedPreviewDtoList
                        Timber.d("list value :")
                        for ((index, feed) in recommendedFeedList.value!!.withIndex()) {
                            Timber.d("$index 번째 피드 : $feed")
                        }
                    },
                    onError = {
                        Timber.d("it.errorBody : ${it.errorBody}")
                        Timber.d("it.headers : ${it.headers}")
                        Timber.d("it.raw : ${it.raw}")
                        Timber.d("it.response : ${it.response}")
                        Timber.d("it.statusCode : ${it.statusCode}")
                    },
                    onException = {
                        Timber.d("it.message : ${it.message}")
                        Timber.d("it.exception : ${it.exception}")
                    }
                )
            }
    }

    suspend fun getUserFeedList() {
        homeRepository.getUserFeedList(prefStorage.userId)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _userFeedList.value = it.data?.embedded?.feedPreviewDtoList
                        Timber.d("list value : ${userFeedList.value}")
                    },
                    onError = {
                        Timber.d("it.errorBody : ${it.errorBody}")
                        Timber.d("it.headers : ${it.headers}")
                        Timber.d("it.raw : ${it.raw}")
                        Timber.d("it.response : ${it.response}")
                        Timber.d("it.statusCode : ${it.statusCode}")
                    },
                    onException = {
                        Timber.d("it.message : ${it.message}")
                        Timber.d("it.exception : ${it.exception}")
                    }
                )
            }
    }

    suspend fun getUserInfo() {
        homeRepository.getUserInfo(prefStorage.userId)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        it.data?.let{ userInfoResponse ->
                            _userInfo.value = userInfoResponse
                        }
                    },
                    onError = {
                        Timber.d("it.errorBody : ${it.errorBody}")
                        Timber.d("it.headers : ${it.headers}")
                        Timber.d("it.raw : ${it.raw}")
                        Timber.d("it.response : ${it.response}")
                        Timber.d("it.statusCode : ${it.statusCode}")
                    },
                    onException = {
                        Timber.d("it.message : ${it.message}")
                        Timber.d("it.exception : ${it.exception}")
                    }
                )
            }
    }

}