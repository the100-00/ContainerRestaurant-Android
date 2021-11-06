package container.restaurant.android.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import container.restaurant.android.R
import container.restaurant.android.data.PrefStorage
import container.restaurant.android.data.repository.AuthRepository
import container.restaurant.android.data.request.UpdateProfileRequest
import container.restaurant.android.data.response.NicknameDuplicationCheckResponse
import container.restaurant.android.data.response.ProfileResponse
import container.restaurant.android.data.response.UserInfoResponse
import container.restaurant.android.util.Event
import container.restaurant.android.util.SingleLiveEvent
import container.restaurant.android.util.handleApiResponse
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.regex.Pattern

class AuthViewModel(
    private val prefStorage: PrefStorage,
    private val authRepository: AuthRepository
) : ViewModel() {

    private class ValidationCheck {
        companion object {
            const val MIN_LENGTH = 2
            const val MAX_LENGTH = 20
            val impossibleLetterPattern: Pattern = Pattern.compile("[^가-힣a-zA-Z\\d ]")
            val koreanPattern: Pattern = Pattern.compile("[가-힣]")
            val enNumSpacePattern: Pattern = Pattern.compile("[a-zA-Z\\d ]")
        }
    }

    val userInfoResponse = MutableLiveData<UserInfoResponse>()
    val infoMessage = MutableLiveData<String>()

    private val _signInWithAccessTokenResult = MutableLiveData<ProfileResponse>()
    val signInWithAccessTokenResult: LiveData<ProfileResponse> = _signInWithAccessTokenResult

    private val _signedUpId = SingleLiveEvent<Int>()
    val signedUpId: LiveData<Int> = _signedUpId

    private val _isCompleteButtonClicked = MutableLiveData<Event<Boolean>>()
    val isCompleteButtonClicked: LiveData<Event<Boolean>> = _isCompleteButtonClicked

    private val _isNicknameNull = SingleLiveEvent<Void>()
    val isNicknameNull: LiveData<Void> = _isNicknameNull

    private val _isSignInWithAccessTokenSuccess = SingleLiveEvent<Void>()
    val isSignInWithAccessTokenSuccess: LiveData<Void> = _isSignInWithAccessTokenSuccess

    private val _isGenerateAccessTokenSuccess = SingleLiveEvent<Void>()
    val isGenerateAccessTokenSuccess: LiveData<Void> = _isGenerateAccessTokenSuccess

    private val _notOurUser = MutableLiveData<Event<Boolean>>()
    val notOurUser: LiveData<Event<Boolean>> = _notOurUser

    private val _errorMessageId = MutableLiveData<Event<Int>>()
    val errorMessageId: LiveData<Event<Int>> = _errorMessageId

    private val _nicknameValidationCheck = MutableLiveData<Event<Boolean>>()
    val nicknameValidationCheck: LiveData<Event<Boolean>> = _nicknameValidationCheck

    private val _nicknameDuplicationCheckResult =
        MutableLiveData<NicknameDuplicationCheckResponse>()
    val nicknameDuplicationCheckResult: LiveData<NicknameDuplicationCheckResponse> =
        _nicknameDuplicationCheckResult

    fun isUserSignIn() = authRepository.isUserSignIn()

    fun onCompleteButtonClick() {
        _isCompleteButtonClicked.value = Event(true)
    }

    fun letterValidationCheck(string: String): Boolean {
        val matcher = ValidationCheck.impossibleLetterPattern.matcher(string)
        if (matcher.find()) return false
        return true
    }

    fun lengthLongValidationCheck(string: String): Boolean {
        var length = 0
        val koreanMatcher = ValidationCheck.koreanPattern.matcher(string)
        while (koreanMatcher.find()) {
            length++
            if (length > ValidationCheck.MAX_LENGTH) return false
        }

        val enNumSpaceMatcher = ValidationCheck.enNumSpacePattern.matcher(string)
        while (enNumSpaceMatcher.find()) {
            length++
            if (length > ValidationCheck.MAX_LENGTH) return false
        }
        return true
    }

    fun lengthShortValidationCheck(string: String): Boolean {
        var length = 0
        val koreanMatcher = ValidationCheck.koreanPattern.matcher(string)
        while (koreanMatcher.find()) {
            length += 2
            if (length >= ValidationCheck.MIN_LENGTH) return true
        }

        val enNumSpaceMatcher = ValidationCheck.enNumSpacePattern.matcher(string)
        while (enNumSpaceMatcher.find()) {
            length++
            if (length >= ValidationCheck.MIN_LENGTH) return true
        }
        return false
    }

    suspend fun nicknameDuplicationCheck(nickname: String) {
        Timber.d("AuthViewModel nicknameDuplicationCheck called")
        authRepository.nicknameDuplicationCheck(
            nickname = nickname,
            onStart = { Timber.d("nicknameDuplicationCheck onStart") },
            onComplete = { Timber.d("nicknameDuplicationCheck onComplete") },
            onError = { Timber.d("nicknameDuplicationCheck onError") })
            .collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        Timber.d("response.headers : ${response.headers}")
                        Timber.d("response.raw : ${response.raw}")
                        Timber.d("response.response : ${response.response}")
                        Timber.d("response.statusCode : ${response.statusCode}")
                        Timber.d("response.data : ${response.data}")
                        response.data?.let {
                            _nicknameDuplicationCheckResult.value = it
                        }
                    }
                    is ApiResponse.Failure.Error -> {
                        Timber.d("response.headers : ${response.headers}")
                        Timber.d("response.raw : ${response.raw}")
                        Timber.d("response.response : ${response.response}")
                        Timber.d("response.statusCode : ${response.statusCode}")
                        Timber.d("response.errorBody : ${response.errorBody}")
                        when (response.statusCode) {
                            StatusCode.BadRequest -> {
                                // 올바르지 않은 닉네임
                            }
                            else -> {
                                _errorMessageId.value = Event(R.string.error_message_other)
                            }
                        }
                    }
                    is ApiResponse.Failure.Exception -> {
                        _errorMessageId.value = Event(R.string.error_message_other)
                        Timber.d("response.message : ${response.message}")
                        Timber.d("response.exception : ${response.exception}")
                    }
                }
            }
    }

    suspend fun generateAccessToken(provider: String, accessToken: String) {
        authRepository.generateAccessToken(
            provider = provider,
            accessToken = accessToken
        ).collect { response ->
            when (response) {
                is ApiResponse.Success -> {
                    if (response.data?.token != null && response.data?.id != null) {
                        storeUserTokenAndId("Bearer ${response.data?.token}", response.data?.id!!)
                    }
                    _isGenerateAccessTokenSuccess.call()
                }
                is ApiResponse.Failure.Error -> {
                    when (response.statusCode) {
                        StatusCode.BadRequest -> {
                            // 올바르지 않은 닉네임
                        }
                        else -> {
                            _errorMessageId.value = Event(R.string.error_message_other)
                        }
                    }
                }
                is ApiResponse.Failure.Exception -> {
                    _errorMessageId.value = Event(R.string.error_message_other)
                }
            }
        }
    }

    suspend fun signInWithAccessToken(
        onNicknameNull: () -> Unit = {},
        onSignInSuccess: (UserInfoResponse) -> Unit = {},
        onInvalidToken: () -> Unit = {}
    ) {
        Timber.d("prefStorage.tokenBearer ${prefStorage.tokenBearer}")
        authRepository.signInWithAccessToken(prefStorage.tokenBearer)
            .collect { response ->
                handleApiResponse(response = response,
                    onSuccess = {
                        if (it.data?.nickname == null) {
                            onNicknameNull()
                            _isNicknameNull.call()
                        } else {
                            it.data?.let { userInfoResponse ->
                                onSignInSuccess(userInfoResponse)
                                this.userInfoResponse.value = it.data
                            }
                            _isSignInWithAccessTokenSuccess.call()
                        }
                    },
                    onError = {
                        if (it.statusCode == StatusCode.Unauthorized) {
                            onInvalidToken()
                        }
                    }
                )
            }
    }

    suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest? = null) {
        authRepository.updateProfile(
            prefStorage.tokenBearer,
            prefStorage.userId,
            updateProfileRequest
        )
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        Timber.d("updateProfile complete")
                    }
                )
            }
    }

    private fun storeUserTokenAndId(token: String, id: Int) {
        authRepository.storeUserTokenAndId(token, id)
    }
}
