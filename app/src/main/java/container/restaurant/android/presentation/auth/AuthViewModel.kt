package container.restaurant.android.presentation.auth

import container.restaurant.android.data.AuthRepository

interface AuthViewModelDelegate {
    fun isUserSignIn(): Boolean
}

internal class AuthViewModel(
    private val authRepository: AuthRepository
) : AuthViewModelDelegate {

    override fun isUserSignIn() = authRepository.isUserSignIn()
}