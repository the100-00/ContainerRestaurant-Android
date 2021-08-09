package container.restaurant.android.data.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.*
import container.restaurant.android.data.remote.MyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MyRepository(private val myService: MyService) {

    @WorkerThread
    suspend fun getUserInfo(userId: Int) = flow {
        val response = myService.userInfo(userId)
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
    suspend fun getContract() = flow {
        val response = myService.contract()
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
    suspend fun getMyFeed(userId: Int) = flow {
        val response = myService.myFeed(userId)
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
    suspend fun getMyScrapFeed(userId: Int) = flow {
        val response = myService.myScrapFeed(userId)
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

//    @WorkerThread
//    fun tempLogin(onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
//        val response = newApiService.getTempLogin()
//        response.suspendOnSuccess {
//            data?.let { str ->
//                emit(str)
//            }
//        }.onError { map(ErrorResponseMapper) { onError(message) } }
//            .onException { onError(message) }
//    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
//
//    @WorkerThread
//    fun getMyUser(onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
//        val response = newApiService.getUser(CommUtils.cookieForm)
//        response.suspendOnSuccess {
//            data?.let { userResponse ->
//                emit(userResponse)
//            }
//        }.onError { map(ErrorResponseMapper) { onError(message) } }
//            .onException { onError(message) }
//    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
//
//    @WorkerThread
//    fun updateProfile(id: Int, nickname: String, profile: String, onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
//        val updateUserRequest = UpdateUserRequest(nickname, profile, null)
//        val response = newApiService.updateProfile(CommUtils.cookieForm, id, updateUserRequest)
//        response.suspendOnSuccess {
//            data?.let { updateProfileResponse ->
//                emit(updateProfileResponse)
//            }
//        }.onError { map(ErrorResponseMapper) { onError(message) } }
//            .onException { onError(message) }
//    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
//
//    @WorkerThread
//    fun getMyFeed(id: Int, onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
//        val response = newApiService.getMyFeed(CommUtils.cookieForm, id)
//        response.suspendOnSuccess {
//            data?.let { myFeedResponse ->
//                emit(myFeedResponse)
//            }
//        }.onError { map(ErrorResponseMapper) { onError(message) } }
//            .onException { onError(message) }
//    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
//
//    @WorkerThread
//    fun getMyScrapFeed(id: Int, onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
//        val response = newApiService.getMyScrapFeed(CommUtils.cookieForm, id)
//        response.suspendOnSuccess {
//            data?.let { myFeedResponse ->
//                emit(myFeedResponse)
//            }
//        }.onError { map(ErrorResponseMapper) { onError(message) } }
//            .onException { onError(message) }
//    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
//
//    @WorkerThread
//    fun getMyFavorite(onStart: () -> Unit, onComplete: () -> Unit, onError:(String?) -> Unit) = flow {
//        val response = newApiService.getFavoriteRestaurant(CommUtils.cookieForm)
//        response.suspendOnSuccess {
//            data?.let { myFavoriteResponse ->
//                emit(myFavoriteResponse)
//            }
//        }.onError { map(ErrorResponseMapper) { onError(message) } }
//            .onException { onError(message) }
//    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

}