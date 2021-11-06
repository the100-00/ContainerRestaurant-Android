package container.restaurant.android.data.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.*
import container.restaurant.android.data.PrefStorage
import container.restaurant.android.data.remote.AuthService
import container.restaurant.android.data.request.SignUpWithAccessTokenRequest
import container.restaurant.android.data.request.UpdateProfileRequest
import container.restaurant.android.data.response.NicknameDuplicationCheckResponse
import container.restaurant.android.data.response.ProfileResponse
import container.restaurant.android.data.response.SignUpWithAccessTokenResponse
import container.restaurant.android.data.response.UserInfoResponse
import container.restaurant.android.util.ErrorResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber


interface AuthRepository {
    fun isUserSignIn(): Boolean
    suspend fun signInWithAccessToken(
        tokenBearer: String
    ): Flow<ApiResponse<UserInfoResponse>>

    suspend fun nicknameDuplicationCheck(
        nickname: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<ApiResponse<NicknameDuplicationCheckResponse>>

    suspend fun generateAccessToken(
        provider: String,
        accessToken: String
    ): Flow<ApiResponse<SignUpWithAccessTokenResponse>>

    suspend fun updateProfile(
        tokenBearer: String,
        userId: Int
        , updateProfileRequest: UpdateProfileRequest? = null
    ): Flow<ApiResponse<ProfileResponse>>

    fun storeUserTokenAndId(token: String, userId: Int)
}

internal class AuthDataRepository(
    private val prefStorage: PrefStorage,
    private val authService: AuthService
) : AuthRepository {

    override fun isUserSignIn(): Boolean {
        return prefStorage.isUserSignIn
    }

    @WorkerThread
    override suspend fun signInWithAccessToken(
        tokenBearer: String,
    ) = flow {
        Timber.d("AuthDataRepository signInWithAccessToken called")
        val response =
            authService.signInWithAccessToken(tokenBearer)
        response
            .suspendOnSuccess {
                Timber.d("signInWithAccessToken onSuccess")
                emit(this)
            }
            .suspendOnError {
                Timber.d("signInWithAccessToken onError")
                emit(this)
            }
            .suspendOnException {
                Timber.d("signInWithAccessToken onException")
                emit(this)
            }
    }.flowOn(Dispatchers.IO)

    // 입력한 onError 상관 없이 에러 로그 나옴, onStart랑 onCompletion은 나오는데 sandwich시리즈가 결과가 안나옴
    // sandwich 시리즈는 response의 응답인데, onStart랑 onCompletion은 flow의 콜백임
    // 이걸 활용하려는 이유는 생명주기에 따른 콜백 사용이 간편하기 때문. 근데 왜 안될까??
    // 문제는 safeApiCall을 해도 suspend할 생각이 없음...
    // 원인은 addCallAdapterFactory를 안해줘서 그랬음!! 이게 있어야 Exception을 잡을 수 있음
    @WorkerThread
    override suspend fun nicknameDuplicationCheck(
        nickname: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        Timber.d("AuthDataRepository nicknameDuplicationCheck called")
        val response = authService.nicknameDuplicationCheck(nickname)
        response
            .suspendOnSuccess {
                emit(this)
            }
            .suspendOnError {
                emit(this)
                Timber.d("errorBody : ${this.errorBody}")
                Timber.d("statusCode : ${this.statusCode}")
                Timber.d("response : ${this.response}")
                Timber.d("raw : ${this.raw}")
                Timber.d("headers : ${this.headers}")
                map(ErrorResponseMapper) { onError(message) }
            }
            .suspendOnException {
                emit(this)
                onError(message)
            }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    override suspend fun generateAccessToken(
        provider: String,
        accessToken: String
    ): Flow<ApiResponse<SignUpWithAccessTokenResponse>> = flow {
        authService.generateAccessToken(SignUpWithAccessTokenRequest(provider, accessToken))
            .suspendOnSuccess {
                emit(this)
            }
            .suspendOnError {
                emit(this)
            }
            .suspendOnException {
                emit(this)
            }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateProfile(tokenBearer: String, userId: Int, updateProfileRequest: UpdateProfileRequest?): Flow<ApiResponse<ProfileResponse>> = flow {
        authService.updateProfile(tokenBearer, userId, updateProfileRequest)
            .suspendOnSuccess {
                emit(this)
            }
            .suspendOnError {
                emit(this)
            }
            .suspendOnException {
                emit(this)
            }
    }.flowOn(Dispatchers.IO)

    override fun storeUserTokenAndId(token: String, userId: Int) {
        prefStorage.isUserSignIn = true
        prefStorage.tokenBearer = token
        prefStorage.userId = userId
    }
}