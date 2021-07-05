package container.restaurant.android.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import container.restaurant.android.data.repository.AuthRepository
import okhttp3.ResponseBody
import timber.log.Timber

interface AuthViewModelDelegate {
    fun isUserSignIn(): Boolean
}

internal class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel(), AuthViewModelDelegate {

    override fun isUserSignIn() = authRepository.isUserSignIn()
    fun signInWithAccessToken(provider: String, accessToken: String): LiveData<ResponseBody> {
        Timber.d("AuthViewModel signInWithAccessToken called")
        return authRepository.signInWithAccessToken(
            provider = provider,
            accessToken = accessToken,
            onStart = { Timber.d("signInWithAccessToken onStart") },
            onComplete = { Timber.d("signInWithAccessToken onComplete") },
            onError = { Timber.d("signInWithAccessToken onError") }).asLiveData()
    }

}
