package container.restaurant.android.di

import container.restaurant.android.BuildConfig
import container.restaurant.android.data.remote.FeedService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val BASE_URL = if (BuildConfig.DEBUG) {
    "http://ec2-52-78-66-184.ap-northeast-2.compute.amazonaws.com/"
} else {
    ""
}


val networkModule = module {
    single { createOkHttp() }
    single { createRetrofit(get(), BASE_URL) }
    single { createFeedService(get()) }
}

fun createFeedService(retrofit: Retrofit): FeedService {
    return retrofit.create(FeedService::class.java)
}

fun createOkHttp(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .connectTimeout(15L, TimeUnit.SECONDS)
        .readTimeout(15L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}