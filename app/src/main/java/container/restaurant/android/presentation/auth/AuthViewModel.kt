package container.restaurant.android.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import container.restaurant.android.R
import container.restaurant.android.data.repository.AuthRepository
import container.restaurant.android.data.request.UpdateProfileRequest
import container.restaurant.android.data.response.NicknameDuplicationCheckResponse
import container.restaurant.android.data.response.ProfileResponse
import container.restaurant.android.util.Event
import container.restaurant.android.util.SingleLiveEvent
import container.restaurant.android.util.handleApiResponse
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.regex.Pattern

internal class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private class ValidationCheck {
        companion object{
            const val MIN_LENGTH = 2
            const val MAX_LENGTH = 20
            val impossibleLetterPattern: Pattern = Pattern.compile("[^가-힣a-zA-Z\\d ]")
            val koreanPattern: Pattern = Pattern.compile("[가-힣]")
            val enNumSpacePattern: Pattern = Pattern.compile("[a-zA-Z\\d ]")
        }
    }

    val infoMessage = MutableLiveData<String>()

    private val _signInWithAccessTokenResult = MutableLiveData<ProfileResponse>()
    val signInWithAccessTokenResult:LiveData<ProfileResponse> = _signInWithAccessTokenResult

    private val _signedUpId = SingleLiveEvent<Int>()
    val signedUpId: LiveData<Int> = _signedUpId

    private val _isCompleteButtonClicked = MutableLiveData<Event<Boolean>>()
    val isCompleteButtonClicked: LiveData<Event<Boolean>> = _isCompleteButtonClicked

    private val _signInWithAccessTokenSuccess = MutableLiveData<Event<Boolean>>()
    val signInWithAccessTokenSuccess : LiveData<Event<Boolean>> = _signInWithAccessTokenSuccess

    private val _notOurUser = MutableLiveData<Event<Boolean>>()
    val notOurUser: LiveData<Event<Boolean>> = _notOurUser

    private val _errorMessageId = MutableLiveData<Event<Int>>()
    val errorMessageId: LiveData<Event<Int>> = _errorMessageId

    private val _nicknameValidationCheck = MutableLiveData<Event<Boolean>>()
    val nicknameValidationCheck:LiveData<Event<Boolean>> = _nicknameValidationCheck

    private val _nicknameDuplicationCheckResult = MutableLiveData<NicknameDuplicationCheckResponse>()
    val nicknameDuplicationCheckResult : LiveData<NicknameDuplicationCheckResponse> = _nicknameDuplicationCheckResult

    fun isUserSignIn() = authRepository.isUserSignIn()

    fun onCompleteButtonClick() {
        _isCompleteButtonClicked.value = Event(true)
    }

    fun letterValidationCheck(string: String):Boolean {
        val matcher = ValidationCheck.impossibleLetterPattern.matcher(string)
        if(matcher.find()) return false
        return true
    }

    fun lengthLongValidationCheck(string: String): Boolean {
        var length = 0
        val koreanMatcher = ValidationCheck.koreanPattern.matcher(string)
        while(koreanMatcher.find()){
            length++
            if(length>ValidationCheck.MAX_LENGTH) return false
        }

        val enNumSpaceMatcher= ValidationCheck.enNumSpacePattern.matcher(string)
        while(enNumSpaceMatcher.find()){
            length++
            if(length>ValidationCheck.MAX_LENGTH) return false
        }
        return true
    }

    fun lengthShortValidationCheck(string: String): Boolean{
        var length = 0
        val koreanMatcher = ValidationCheck.koreanPattern.matcher(string)
        while(koreanMatcher.find()){
            length+=2
            if(length>=ValidationCheck.MIN_LENGTH) return true
        }

        val enNumSpaceMatcher= ValidationCheck.enNumSpacePattern.matcher(string)
        while(enNumSpaceMatcher.find()){
            length++
            if(length>=ValidationCheck.MIN_LENGTH) return true
        }
        return false
    }

    suspend fun signInWithAccessToken(provider: String, accessToken: String) {
        Timber.d("AuthViewModel signInWithAccessToken called")
        authRepository.signInWithAccessToken(
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

    suspend fun nicknameDuplicationCheck(nickname: String) {
        Timber.d("AuthViewModel nicknameDuplicationCheck called")
        authRepository.nicknameDuplicationCheck(
            nickname = nickname,
            onStart = { Timber.d("nicknameDuplicationCheck onStart") },
            onComplete = { Timber.d("nicknameDuplicationCheck onComplete") },
            onError = { Timber.d("nicknameDuplicationCheck onError") })
            .collect { response ->
                when(response){
                    is ApiResponse.Success ->{
                        Timber.d("response.headers : ${response.headers}")
                        Timber.d("response.raw : ${response.raw}")
                        Timber.d("response.response : ${response.response}")
                        Timber.d("response.statusCode : ${response.statusCode}")
                        Timber.d("response.data : ${response.data}")
                        response.data?.let{
                            _nicknameDuplicationCheckResult.value = it
                        }
                    }
                    is ApiResponse.Failure.Error -> {
                        Timber.d("response.headers : ${response.headers}")
                        Timber.d("response.raw : ${response.raw}")
                        Timber.d("response.response : ${response.response}")
                        Timber.d("response.statusCode : ${response.statusCode}")
                        Timber.d("response.errorBody : ${response.errorBody}")
                        when(response.statusCode) {
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

    suspend fun signUpWithAccessToken(provider: String, accessToken: String) {
        authRepository.signUpWithAccessToken(
            provider = provider,
            accessToken = accessToken
        ).collect { response ->
            when(response) {
                is ApiResponse.Success -> {
                    Timber.d("response.headers : ${response.headers}")
                    Timber.d("response.raw : ${response.raw}")
                    Timber.d("response.response : ${response.response}")
                    Timber.d("response.statusCode : ${response.statusCode}")
                    Timber.d("response.data : ${response.data}")
                    response.data?.let{
                        _signedUpId.value = response.headers["Container-Restaurant-User-Id"]?.toInt()
                    }
                }
                is ApiResponse.Failure.Error -> {
                    Timber.d("response.headers : ${response.headers}")
                    Timber.d("response.raw : ${response.raw}")
                    Timber.d("response.response : ${response.response}")
                    Timber.d("response.statusCode : ${response.statusCode}")
                    Timber.d("response.errorBody : ${response.errorBody}")
                    when(response.statusCode) {
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

    suspend fun updateProfile(userId: Int, updateProfileRequest: UpdateProfileRequest? = null){
        authRepository.updateProfile(userId, updateProfileRequest)
            .collect { response ->
               handleApiResponse(
                   response = response,
                   onSuccess = {
                       Timber.d("updateProfile complete")
                   }
               )
            }
    }

    fun storeUserId(id: Int) {
        authRepository.storeUserId(id)
    }
}
