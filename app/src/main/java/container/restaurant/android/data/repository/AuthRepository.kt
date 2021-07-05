package container.restaurant.android.data.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.map
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import container.restaurant.android.data.PrefStorage
import container.restaurant.android.data.remote.AuthService
import container.restaurant.android.data.request.signInWithAccessTokenRequest
import container.restaurant.android.util.ErrorResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import timber.log.Timber


interface AuthRepository {
    fun isUserSignIn(): Boolean
    fun signInWithAccessToken(
        provider: String,
        accessToken: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<ResponseBody>
}

internal class AuthDataRepository(
    private val prefStorage: PrefStorage,
    private val authService: AuthService
) : AuthRepository {

    override fun isUserSignIn(): Boolean {
        return prefStorage.isUserSignIn
    }

    @WorkerThread
    override fun signInWithAccessToken(
        provider: String,
        accessToken: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        Timber.d("AuthDataRepository signInWithAccessToken called")
        val response =
            authService.signInWithAccessToken(signInWithAccessTokenRequest(provider, accessToken))
        response.suspendOnSuccess {
            data?.let {
                emit(it)
                Timber.d("Response body : $it")
            }
        }.onError { map(ErrorResponseMapper) { onError(message) } }
            .onException { onError(message) }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
}