package container.restaurant.android.di

import container.restaurant.android.data.AuthDataRepository
import container.restaurant.android.data.AuthRepository
import container.restaurant.android.data.PrefStorage
import container.restaurant.android.data.SharedPrefStorage
import container.restaurant.android.data.repository.FeedDataRepository
import container.restaurant.android.data.repository.FeedRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataModule = module {
    single<PrefStorage> { SharedPrefStorage(androidContext()) }
    single<AuthRepository> { AuthDataRepository(get()) }
    single<FeedRepository> { FeedDataRepository(get()) }
}
