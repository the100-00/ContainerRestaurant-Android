package container.restaurant.android.presentation.feed.write

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import container.restaurant.android.data.response.SearchLocalResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v1/search/local.json")
    suspend fun getSearchPlace(@Query("query") query: String, @Query("display") display: Int? = 5, @Query("start") start: Int? = 1) : ApiResponse<SearchLocalResponse>

    companion object {

        private const val BASE_URL_NAVER_API = "https://openapi.naver.com/"
        private const val CLIENT_ID = "jJD4IOv0VHThW2p5O3eQ"
        private const val CLIENT_SECRET = "VyRw6tZ3lm"

        fun create(): PlaceService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-Naver-Client-Id", CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", CLIENT_SECRET)
                    .build()
                return@Interceptor it.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL_NAVER_API)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
                .build()
                .create(PlaceService::class.java)
        }
    }
}