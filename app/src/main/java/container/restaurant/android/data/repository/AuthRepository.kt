package container.restaurant.android.data


interface AuthRepository {
    fun isUserSignIn(): Boolean
}

internal class AuthDataRepository(
    private val prefStorage: PrefStorage
): AuthRepository {

    override fun isUserSignIn(): Boolean {
        return prefStorage.isUserSignIn
    }
}