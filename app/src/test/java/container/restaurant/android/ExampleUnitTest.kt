package container.restaurant.android

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.junit.Test
import java.io.IOException

class Test {

    val scope = CoroutineScope(Job())

    @Test
    fun test() {
        scope.launch {
            suggestionSafeApiCall { test1() }
        }
    }

    private suspend fun test1() {
        println("invoke")
        throw IllegalAccessError("error")
    }
}

suspend fun <T> suggestionSafeApiCall(apiCall: suspend () -> T) {
    coroutineScope {
        try {
            println("invoke")
            apiCall.invoke()
        } catch (throwable: Throwable) {
            println(throwable.message)
//            when (throwable) {
//                is IOException -> {
//                }
//                else -> {
//
//                }
//            }
        }
    }
}
