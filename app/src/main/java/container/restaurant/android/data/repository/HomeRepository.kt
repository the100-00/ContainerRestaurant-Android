package container.restaurant.android.data.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.map
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import container.restaurant.android.data.remote.HomeService
import container.restaurant.android.data.request.SignInWithAccessTokenRequest
import container.restaurant.android.util.ErrorResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class HomeRepository(private val homeService: HomeService) {
    @WorkerThread
    suspend fun signInWithAccessToken(
        provider: String,
        accessToken: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        Timber.d("AuthDataRepository signInWithAccessToken called")
        val response =
            homeService.signInWithAccessToken(SignInWithAccessTokenRequest(provider, accessToken))
        response
            .suspendOnSuccess {
                emit(this)
            }
            .suspendOnError {
                emit(this)
                map(ErrorResponseMapper) { onError(message) }
            }
            .suspendOnException {
                emit(this)
                onError(message)
            }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    suspend fun getHomeInfo() = flow {
        val response = homeService.homeInfo()
        response
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

    @WorkerThread
    suspend fun getRecommendedFeedList() = flow {
        val response = homeService.recommendedFeedList()
        response
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

    @WorkerThread
    suspend fun getUserFeedList(userId: Int) = flow {
        val response = homeService.userFeedList(userId)
        response
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

    @WorkerThread
    suspend fun getUserInfo(userId: Int) = flow {
        val response = homeService.userInfo(userId)
        response
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
}