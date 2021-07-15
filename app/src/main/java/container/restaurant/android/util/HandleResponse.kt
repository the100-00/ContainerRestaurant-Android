package container.restaurant.android.util

import com.skydoves.sandwich.ApiResponse

fun <T> handleApiResponse(
    response: ApiResponse<T>,
    onSuccess : (ApiResponse.Success<T>)->Unit ,
    onError : (ApiResponse.Failure.Error<T>)->Unit,
    onException: (ApiResponse.Failure.Exception<T>)->Unit
){
    when(response) {
        is ApiResponse.Success<T> -> onSuccess(response)
        is ApiResponse.Failure.Error<T> -> onError(response)
        is ApiResponse.Failure.Exception<T> -> onException(response)
    }
}