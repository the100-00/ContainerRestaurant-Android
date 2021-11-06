package container.restaurant.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import container.restaurant.android.data.PrefStorage
import container.restaurant.android.data.repository.HomeRepository
import container.restaurant.android.data.response.FeedListResponse
import container.restaurant.android.data.response.HomeInfoResponse
import container.restaurant.android.data.response.ProfileResponse
import container.restaurant.android.util.Event
import container.restaurant.android.util.handleApiResponse
import kotlinx.coroutines.flow.collect
import timber.log.Timber

internal class HomeViewModel(
    private val prefStorage: PrefStorage,
    private val homeRepository: HomeRepository
) : ViewModel() {

    val navToAllContainerFeed = MutableLiveData<Event<Unit>>()

    private val _signInWithAccessTokenResult = MutableLiveData<ProfileResponse>()
    val signInWithAccessTokenResult: LiveData<ProfileResponse> = _signInWithAccessTokenResult

    private val _loginId = MutableLiveData<Int>()
    val loginId: LiveData<Int> = _loginId

    private val _totalFeedCount= MutableLiveData<Int>()
    val totalFeedCount: LiveData<Int> = _totalFeedCount

    private val _phrase = MutableLiveData<String>()
    val phrase:LiveData<String> = _phrase

    private val _latestWriterProfileList = MutableLiveData<List<String>>()
    val latestWriterProfileList: LiveData<List<String>> = _latestWriterProfileList

    private val _bannerList = MutableLiveData<List<HomeInfoResponse.Banner>>()
    val bannerList: LiveData<List<HomeInfoResponse.Banner>> = _bannerList

    private val _recommendedFeedList = MutableLiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>>()
    val recommendedFeedList: LiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>> = _recommendedFeedList

    private val _userFeedList = MutableLiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>>()
    val userFeedList: LiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>> = _userFeedList

    val userNickName = MutableLiveData<String>()

    val userProfileUrl = MutableLiveData<String>()

    val userProfileRes = MutableLiveData<Int>()

    val userLevelTitle = MutableLiveData<String>()

    val userFeedCount = MutableLiveData<Int>()

    val homeIconResByUserLevel = MutableLiveData<Int>()

    private val _isNavToAllContainerFeedClicked = MutableLiveData<Event<Boolean>>()
    val isNavToAllContainerFeedClicked: LiveData<Event<Boolean>> = _isNavToAllContainerFeedClicked

    private val _isNavToMyContainerFeedClicked = MutableLiveData<Event<Boolean>>()
    val isNavToMyContainerFeedClicked: LiveData<Event<Boolean>> = _isNavToMyContainerFeedClicked

    private val _isBackButtonClicked = MutableLiveData<Event<Boolean>>()
    val isBackButtonClicked: LiveData<Event<Boolean>> = _isBackButtonClicked

    fun onClickMyContainerFeed() {
        _isNavToMyContainerFeedClicked.value = Event(true)
    }

    fun onClickAllContainerFeed() {
        _isNavToAllContainerFeedClicked.value = Event(true)
    }

    fun onClickBackButton() {
        _isBackButtonClicked.value = Event(true)
    }

    fun isUserSignIn(): Boolean {
        return prefStorage.isUserSignIn
    }

    suspend fun getHomeInfo() {
        homeRepository.getHomeInfo(prefStorage.tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        userFeedCount.value = it.data?.userFeedCount
                        _totalFeedCount.value = it.data?.totalFeedCount
                        userLevelTitle.value = it.data?.userLevelTitle
                        userProfileUrl.value = it.data?.userProfileUrl
                        _phrase.value = it.data?.phrase
                        _latestWriterProfileList.value = it.data?.latestWriterProfileList
                        _bannerList.value = it.data?.bannerList

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
//        homeRepository.getUserFeedList(prefStorage.token)
//            .collect { response ->
//                handleApiResponse(
//                    response = response,
//                    onSuccess = {
//                        _userFeedList.value = it.data?.embedded?.feedPreviewDtoList
//                        Timber.d("list value : ${userFeedList.value}")
//                    },
//                    onError = {
//                        Timber.d("it.errorBody : ${it.errorBody}")
//                        Timber.d("it.headers : ${it.headers}")
//                        Timber.d("it.raw : ${it.raw}")
//                        Timber.d("it.response : ${it.response}")
//                        Timber.d("it.statusCode : ${it.statusCode}")
//                    },
//                    onException = {
//                        Timber.d("it.message : ${it.message}")
//                        Timber.d("it.exception : ${it.exception}")
//                    }
//                )
//            }
    }

    suspend fun getUserInfo() {
//        homeRepository.getUserInfo(prefStorage.token)
//            .collect { response ->
//                handleApiResponse(
//                    response = response,
//                    onSuccess = {
//                        _userNickName.value = it.data?.nickname
//                        _userFeedCount.value = it.data?.feedCount
//                        _userProfileUrl.value = it.data?.profile
//                        _userLevelTitle.value = it.data?.levelTitle
//                        Timber.d("userInfo value ${it.data}")
//                        Timber.d("_userNickName value ${_userNickName.value}")
//                        Timber.d("_userLevelTitle value ${_userLevelTitle.value}")
//                        Timber.d("_userFeedCount value ${_userFeedCount.value}")
//                        Timber.d("_userProfileUrl value ${_userProfileUrl.value}")
//                    },
//                    onError = {
//                        Timber.d("it.errorBody : ${it.errorBody}")
//                        Timber.d("it.headers : ${it.headers}")
//                        Timber.d("it.raw : ${it.raw}")
//                        Timber.d("it.response : ${it.response}")
//                        Timber.d("it.statusCode : ${it.statusCode}")
//                    },
//                    onException = {
//                        Timber.d("it.message : ${it.message}")
//                        Timber.d("it.exception : ${it.exception}")
//                    }
//                )
//            }
    }

}