package container.restaurant.android.util

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            throw e
        }

        val headers = response.headers

        for (i in 0 until headers.size) {
            if(headers.name(i).equals("Set-Cookie", true))
                CommUtils.cookieForm = headers.value(i)
        }

        return response
    }
}