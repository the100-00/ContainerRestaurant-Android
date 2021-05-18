package com.tak8997.github.domain


sealed class ResultState<T> {

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<T>(val data: T) : ResultState<T>()

    /**
     * A state to show an error
     */
    data class Error<T>(val error: ErrorEntity.Error?) : ResultState<T>()
}

sealed class ErrorEntity {
    data class Error(val errorCode: Any?, val errorMessage: String? = "") : ErrorEntity()
}