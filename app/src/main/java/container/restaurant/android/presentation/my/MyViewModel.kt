package container.restaurant.android.presentation.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import container.restaurant.android.data.PrefStorage
import container.restaurant.android.data.repository.MyRepository
import container.restaurant.android.data.response.FeedListResponse
import container.restaurant.android.util.Event
import container.restaurant.android.util.handleApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class MyViewModel(private val prefStorage: PrefStorage, private val myRepository: MyRepository) :
    ViewModel() {
    val getErrorMsg = MutableLiveData<String>()
    val viewLoading = MutableLiveData<Boolean>()

    val loginChk = MutableStateFlow(false)

    private val _userFeedList =
        MutableLiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>>()
    val userFeedList: LiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>> =
        _userFeedList

    private val _userNickName = MutableLiveData<String>()
    val userNickName: LiveData<String> = _userNickName

    private val _userProfileUrl = MutableLiveData<String>()
    val userProfileUrl: LiveData<String> = _userProfileUrl

    val userProfileRes = MutableLiveData<Int>()

    private val _userLevelTitle = MutableLiveData<String>()
    val userLevelTitle: LiveData<String> = _userLevelTitle

    private val _userFeedCount = MutableLiveData<Int>()
    val userFeedCount: LiveData<Int> = _userFeedCount

    private val _userScrapCount = MutableLiveData<Int>()
    val userScrapCount: LiveData<Int> = _userScrapCount

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val _userBookmarkedCount = MutableLiveData<Int>()
    val userBookmarkedCount: LiveData<Int> = _userBookmarkedCount

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    private val _termsOfServiceTitle = MutableLiveData<String>()
    val termsOfServiceTitle: LiveData<String> = _termsOfServiceTitle

    private val _termsOfServiceArticle = MutableLiveData<String>()
    val termsOfServiceArticle: LiveData<String> = _termsOfServiceArticle

    private val _privacyPolicyTitle = MutableLiveData<String>()
    val privacyPolicyTitle: LiveData<String> = _privacyPolicyTitle

    private val _privacyPolicyArticle = MutableLiveData<String>()
    val privacyPolicyArticle: LiveData<String> = _privacyPolicyArticle

    private val _myFeedList = MutableLiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>>()
    val myFeedList: LiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>> = _myFeedList

    private val _myScrapFeedList = MutableLiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>>()
    val myScrapFeedList: LiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>> = _myFeedList

    private val _isSettingButtonClicked = MutableLiveData<Event<Boolean>>()
    val isSettingButtonClicked: LiveData<Event<Boolean>> = _isSettingButtonClicked

    private val _isMyFeedButtonClicked = MutableLiveData<Event<Boolean>>()
    val isMyFeedButtonClicked: LiveData<Event<Boolean>> = _isMyFeedButtonClicked

    private val _isScrapFeedButtonClicked = MutableLiveData<Event<Boolean>>()
    val isScrapFeedButtonClicked: LiveData<Event<Boolean>> = _isScrapFeedButtonClicked

    private val _isBookmarkedRestaurantButtonClicked = MutableLiveData<Event<Boolean>>()
    val isBookmarkedRestaurantButtonClicked: LiveData<Event<Boolean>> = _isBookmarkedRestaurantButtonClicked

    private val _isNicknameChangeButtonClicked = MutableLiveData<Event<Boolean>>()
    val isNicknameChangeButtonClicked: LiveData<Event<Boolean>> = _isNicknameChangeButtonClicked

    private val _isBackButtonClicked = MutableLiveData<Event<Boolean>>()
    val isBackButtonClicked: LiveData<Event<Boolean>> = _isBackButtonClicked

    private val _isPrivacyPolicyButtonClicked = MutableLiveData<Event<Boolean>>()
    val isPrivacyPolicyButtonClicked: LiveData<Event<Boolean>> = _isPrivacyPolicyButtonClicked

    private val _isTermsOfServiceButtonClicked = MutableLiveData<Event<Boolean>>()
    val isTermsOfPolicyButtonClicked: LiveData<Event<Boolean>> = _isTermsOfServiceButtonClicked

    private val _isSignOutButtonClicked = MutableLiveData<Event<Boolean>>()
    val isSignOutButtonClicked: LiveData<Event<Boolean>> = _isSignOutButtonClicked

    private val _isWithdrawalButtonClicked = MutableLiveData<Event<Boolean>>()
    val isWithdrawalButtonClicked: LiveData<Event<Boolean>> = _isWithdrawalButtonClicked

    fun onSettingButtonClick() {
        _isSettingButtonClicked.value = Event(true)
        _isSettingButtonClicked.value = Event(false)
    }

    fun onMyFeedButtonClick() {
        _isMyFeedButtonClicked.value = Event(true)
        _isMyFeedButtonClicked.value = Event(false)
    }

    fun onScrapFeedButtonClick() {
        _isScrapFeedButtonClicked.value = Event(true)
        _isScrapFeedButtonClicked.value = Event(false)
    }

    fun onSignOutButtonClick() {
        _isSignOutButtonClicked.value = Event(true)
        _isSignOutButtonClicked.value = Event(false)
    }

    fun onWithdrawalButtonClick() {
        _isWithdrawalButtonClicked.value = Event(true)
        _isWithdrawalButtonClicked.value = Event(false)
    }

    fun onBookmarkedRestaurantButtonClick() {
        _isBookmarkedRestaurantButtonClicked.value = Event(true)
        _isBookmarkedRestaurantButtonClicked.value = Event(false)
    }

    fun onNicknameChangeButtonClick() {
        _isNicknameChangeButtonClicked.value = Event(true)
        _isNicknameChangeButtonClicked.value = Event(false)
    }

    fun onBackButtonClick() {
        _isBackButtonClicked.value = Event(true)
    }

    fun onPrivacyPolicyButtonClick() {
        _isPrivacyPolicyButtonClicked.value = Event(true)
        _isPrivacyPolicyButtonClicked.value = Event(false)
    }

    fun onTermsOfServiceButtonClick() {
        _isTermsOfServiceButtonClicked.value = Event(true)
        _isTermsOfServiceButtonClicked.value = Event(false)
    }

    fun isUserSignIn(): Boolean {
        return prefStorage.isUserSignIn
    }

    suspend fun getUserInfo() {
        myRepository.getUserInfo(prefStorage.userId)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _userNickName.value = it.data?.nickname ?: "용기낸 식당"
                        _userFeedCount.value = it.data?.feedCount
                        _userProfileUrl.value = it.data?.profile
                        _userLevelTitle.value = it.data?.levelTitle
                        _userScrapCount.value = it.data?.scrapCount
                        _userId.value = it.data?.id
                        _userEmail.value = it.data?.email
                        _userBookmarkedCount.value = it.data?.bookmarkedCount
                        Timber.d("userInfo value ${it.data}")
                        Timber.d("_userNickName value ${_userNickName.value}")
                        Timber.d("_userLevelTitle value ${_userLevelTitle.value}")
                        Timber.d("_userFeedCount value ${_userFeedCount.value}")
                        Timber.d("_userProfileUrl value ${_userProfileUrl.value}")
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

    suspend fun getTermsOfService() {
        myRepository.getContract()
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        val termsOfService =it.data?.embedded?.contractInfoDTOList?.find { contract ->
                            contract.title == "용기낸식당 이용약관"
                        }
                        _termsOfServiceTitle.value = termsOfService?.title
                        _termsOfServiceArticle.value = termsOfService?.article
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

    suspend fun getPrivacyPolicy() {
        myRepository.getContract()
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        val privacyPolicy = it.data?.embedded?.contractInfoDTOList?.find { contract ->
                            contract.title == "용기낸식당 개인정보 취급방침"
                        }
                        _privacyPolicyTitle.value = privacyPolicy?.title
                        _privacyPolicyArticle.value = privacyPolicy?.article
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

    suspend fun getMyFeedList() {
        myRepository.getMyFeed(prefStorage.userId)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _myFeedList.value = it.data?.embedded?.feedPreviewDtoList
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

    suspend fun getMyScrapFeedList() {
        myRepository.getMyScrapFeed(prefStorage.userId)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _myScrapFeedList.value = it.data?.embedded?.feedPreviewDtoList
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

//    fun updateProfile(id: Int, nickname: String, profile: String) = myRepository.updateProfile(
//        id = id,
//        nickname = nickname,
//        profile = profile,
//        onStart = { viewLoading.postValue(true) },
//        onComplete = { viewLoading.postValue(false) },
//        onError = { getErrorMsg.postValue(it) }
//    ).asLiveData()

}