package container.restaurant.android.data.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.map
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import container.restaurant.android.data.remote.NewApiService
import container.restaurant.android.data.request.UpdateUserRequest
import container.restaurant.android.util.CommUtils
import container.restaurant.android.util.ErrorResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class MyRepository(private val newApiService: NewApiService) {

    @WorkerThread
    fun tempLogin(onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
        val response = newApiService.getTempLogin()
        response.suspendOnSuccess {
            data?.let { str ->
                emit(str)
            }
        }.onError { map(ErrorResponseMapper) { onError(message) } }
            .onException { onError(message) }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getMyUser(onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
        val response = newApiService.getUser(CommUtils.cookieForm)
        response.suspendOnSuccess {
            data?.let { userResponse ->
                emit(userResponse)
            }
        }.onError { map(ErrorResponseMapper) { onError(message) } }
            .onException { onError(message) }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun updateProfile(id: Int, nickname: String, profile: String, onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
        val updateUserRequest = UpdateUserRequest(nickname, profile, null)
        val response = newApiService.updateProfile(CommUtils.cookieForm, id, updateUserRequest)
        response.suspendOnSuccess {
            data?.let { updateProfileResponse ->
                emit(updateProfileResponse)
            }
        }.onError { map(ErrorResponseMapper) { onError(message) } }
            .onException { onError(message) }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getMyFeed(id: Int, onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
        val response = newApiService.getMyFeed(CommUtils.cookieForm, id)
        response.suspendOnSuccess {
            data?.let { myFeedResponse ->
                emit(myFeedResponse)
            }
        }.onError { map(ErrorResponseMapper) { onError(message) } }
            .onException { onError(message) }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getMyScrapFeed(id: Int, onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
        val response = newApiService.getMyScrapFeed(CommUtils.cookieForm, id)
        response.suspendOnSuccess {
            data?.let { myFeedResponse ->
                emit(myFeedResponse)
            }
        }.onError { map(ErrorResponseMapper) { onError(message) } }
            .onException { onError(message) }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getMyFavorite(onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
        val response = newApiService.getFavoriteRestaurant(CommUtils.cookieForm)
        response.suspendOnSuccess {
            data?.let { myFavoriteResponse ->
                emit(myFavoriteResponse)
            }
        }.onError { map(ErrorResponseMapper) { onError(message) } }
            .onException { onError(message) }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

}