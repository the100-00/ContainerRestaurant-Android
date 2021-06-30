package container.restaurant.android.presentation.auth

import androidx.lifecycle.ViewModel
import container.restaurant.android.data.AuthRepository

interface AuthViewModelDelegate {
    fun isUserSignIn(): Boolean
}

internal class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel(), AuthViewModelDelegate {
    override fun isUserSignIn() = authRepository.isUserSignIn()
}