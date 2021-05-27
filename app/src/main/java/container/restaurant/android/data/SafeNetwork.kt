package container.restaurant.android.data

import com.tak8997.github.domain.ErrorEntity
import com.tak8997.github.domain.ResultState
import retrofit2.HttpException
import java.io.IOException

suspend fun <T : Any> safeApiCall(call: suspend () -> T): ResultState<T> {
    return try {
        val response = call()
        ResultState.Success(response)
    } catch (ex: Throwable) {
        ResultState.Error(handleError(ex))
    }
}

private fun handleError(throwable: Throwable): ErrorEntity.Error {
    return when (throwable) {
        is IOException -> {
            ErrorEntity.Error(502, "no internet connection")
        }
        is HttpException -> {
            ErrorEntity.Error(500, "server error: ${throwable.code()}")
        }
        else -> {
            ErrorEntity.Error(503, "unexpected error")
        }
    }
}
