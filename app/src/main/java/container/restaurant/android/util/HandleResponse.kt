package container.restaurant.android.util

import com.skydoves.sandwich.ApiResponse
import timber.log.Timber

fun <T> handleApiResponse(
    response: ApiResponse<T>,
    onSuccess: (ApiResponse.Success<T>) -> Unit,
    onError: (ApiResponse.Failure.Error<T>) -> Unit = {
        Timber.d("it.errorBody : ${it.errorBody}")
        Timber.d("it.headers : ${it.headers}")
        Timber.d("it.raw : ${it.raw}")
        Timber.d("it.response : ${it.response}")
        Timber.d("it.statusCode : ${it.statusCode}")
    },
    onException: (ApiResponse.Failure.Exception<T>) -> Unit = {
        Timber.d("it.message : ${it.message}")
        Timber.d("it.exception : ${it.exception}")
    }
) {
    when (response) {
        is ApiResponse.Success<T> -> onSuccess(response)
        is ApiResponse.Failure.Error<T> -> onError(response)
        is ApiResponse.Failure.Exception<T> -> onException(response)
    }
}