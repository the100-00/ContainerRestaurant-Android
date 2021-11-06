package container.restaurant.android.presentation.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skydoves.sandwich.StatusCode
import container.restaurant.android.data.PrefStorage
import container.restaurant.android.data.repository.AuthRepository
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

    val userNickName = MutableLiveData<String>()
    val userProfileUrl = MutableLiveData<String>()
    val userProfileRes = MutableLiveData<Int>()
    val userLevelTitle = MutableLiveData<String>()
    val userFeedCount = MutableLiveData<Int>()
    val userScrapCount = MutableLiveData<Int>()
    val userId = MutableLiveData<Int>()
    val userBookmarkedCount = MutableLiveData<Int>()

    val userEmail = MutableLiveData<String>()

    private val _termsOfServiceTitle = MutableLiveData<String>()
    val termsOfServiceTitle: LiveData<String> = _termsOfServiceTitle

    private val _termsOfServiceArticle = MutableLiveData<String>()
    val termsOfServiceArticle: LiveData<String> = _termsOfServiceArticle

    val onTermsOfServiceLoad: MediatorLiveData<Boolean> = MediatorLiveData()

    private val _privacyPolicyTitle = MutableLiveData<String>()
    val privacyPolicyTitle: LiveData<String> = _privacyPolicyTitle

    private val _privacyPolicyArticle = MutableLiveData<String>()
    val privacyPolicyArticle: LiveData<String> = _privacyPolicyArticle

    val onPrivacyPolicyLoad: MediatorLiveData<Boolean> = MediatorLiveData()

    private val _myFeedList =
        MutableLiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>>()
    val myFeedList: LiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>> = _myFeedList

    private val _myScrapFeedList =
        MutableLiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>>()
    val myScrapFeedList: LiveData<List<FeedListResponse.FeedPreviewDtoList.FeedPreviewDto>> =
        _myFeedList


    init {
        onTermsOfServiceLoad.addSource(termsOfServiceTitle) {
            onTermsOfServiceLoad.value = isTermsOfServiceLoadDone()
        }
        onTermsOfServiceLoad.addSource(termsOfServiceArticle) {
            onTermsOfServiceLoad.value = isTermsOfServiceLoadDone()
        }
        onPrivacyPolicyLoad.addSource(privacyPolicyTitle) {
            onPrivacyPolicyLoad.value = isPrivacyPolicyLoadDone()
        }
        onPrivacyPolicyLoad.addSource(privacyPolicyArticle) {
            onPrivacyPolicyLoad.value = isPrivacyPolicyLoadDone()
        }
    }

    private fun isTermsOfServiceLoadDone(): Boolean =
        (termsOfServiceTitle.value != null && termsOfServiceArticle.value != null)

    private fun isPrivacyPolicyLoadDone(): Boolean =
        (privacyPolicyTitle.value != null && privacyPolicyArticle.value != null)



    fun isUserSignIn(): Boolean {
        return prefStorage.isUserSignIn
    }

    suspend fun getMyInfo(onInvalidToken:()->Unit = {}) {
        myRepository.getUserInfo(prefStorage.tokenBearer)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        userNickName.value = it.data?.nickname ?: "용기낸 식당"
                        userFeedCount.value = it.data?.feedCount
                        userProfileUrl.value = it.data?.profile
                        userLevelTitle.value = it.data?.levelTitle
                        userScrapCount.value = it.data?.scrapCount
                        userId.value = it.data?.id
                        userEmail.value = it.data?.email
                        userBookmarkedCount.value = it.data?.bookmarkedCount
                        Timber.d("userInfo value ${it.data}")
                        Timber.d("_userNickName value ${userNickName.value}")
                        Timber.d("_userLevelTitle value ${userLevelTitle.value}")
                        Timber.d("_userFeedCount value ${userFeedCount.value}")
                        Timber.d("_userProfileUrl value ${userProfileUrl.value}")
                    },
                    onError = {
                        Timber.d("it.errorBody : ${it.errorBody}")
                        Timber.d("it.headers : ${it.headers}")
                        Timber.d("it.raw : ${it.raw}")
                        Timber.d("it.response : ${it.response}")
                        Timber.d("it.statusCode : ${it.statusCode}")
                        if(it.statusCode== StatusCode.Unauthorized) {
                            onInvalidToken()
                        }
                    },
                    onException = {
                        Timber.d("it.message : ${it.message}")
                        Timber.d("it.exception : ${it.exception}")
                    }
                )
            }
    }

    suspend fun getTermsOfService(afterLoading: () -> Unit) {
        myRepository.getContract()
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        val termsOfService =
                            it.data?.embedded?.contractInfoDTOList?.find { contract ->
                                contract.title.contains("용기낸식당 이용약관")
                            }
                        Timber.d("${termsOfService?.title}")
                        Timber.d("${termsOfService?.article}")
                        _termsOfServiceTitle.value = termsOfService?.title
                        _termsOfServiceArticle.value = termsOfService?.article
                        afterLoading()
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
                        val privacyPolicy =
                            it.data?.embedded?.contractInfoDTOList?.find { contract ->
                                contract.title.contains("용기낸식당 개인정보 취급방침")
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
//        myRepository.getMyFeed(prefStorage.token)
//            .collect { response ->
//                handleApiResponse(
//                    response = response,
//                    onSuccess = {
//                        _myFeedList.value = it.data?.embedded?.feedPreviewDtoList
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

    suspend fun getMyScrapFeedList() {
//        myRepository.getMyScrapFeed(prefStorage.token)
//            .collect { response ->
//                handleApiResponse(
//                    response = response,
//                    onSuccess = {
//                        _myScrapFeedList.value = it.data?.embedded?.feedPreviewDtoList
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

//    fun updateProfile(id: Int, nickname: String, profile: String) = myRepository.updateProfile(
//        id = id,
//        nickname = nickname,
//        profile = profile,
//        onStart = { viewLoading.postValue(true) },
//        onComplete = { viewLoading.postValue(false) },
//        onError = { getErrorMsg.postValue(it) }
//    ).asLiveData()
    /** 클릭 이벤트 **/
    private val _isSettingButtonClicked = MutableLiveData<Event<Boolean>>()
    val isSettingButtonClicked: LiveData<Event<Boolean>> = _isSettingButtonClicked

    private val _isMyFeedButtonClicked = MutableLiveData<Event<Boolean>>()
    val isMyFeedButtonClicked: LiveData<Event<Boolean>> = _isMyFeedButtonClicked

    private val _isScrapFeedButtonClicked = MutableLiveData<Event<Boolean>>()
    val isScrapFeedButtonClicked: LiveData<Event<Boolean>> = _isScrapFeedButtonClicked

    private val _isBookmarkedRestaurantButtonClicked = MutableLiveData<Event<Boolean>>()
    val isBookmarkedRestaurantButtonClicked: LiveData<Event<Boolean>> =
        _isBookmarkedRestaurantButtonClicked

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

}