package container.restaurant.android.di

import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import container.restaurant.android.BuildConfig
import container.restaurant.android.data.remote.FeedService
import container.restaurant.android.data.remote.NewApiService
import container.restaurant.android.util.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
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
    single { newApiCreate() }
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

fun newApiCreate(): NewApiService {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

    val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(HeaderInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
        .build()
        .create(NewApiService::class.java)
}