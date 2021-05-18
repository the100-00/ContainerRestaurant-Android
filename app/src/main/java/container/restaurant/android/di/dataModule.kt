package container.restaurant.android.di

import container.restaurant.android.data.AuthDataRepository
import container.restaurant.android.data.AuthRepository
import container.restaurant.android.data.PrefStorage
import container.restaurant.android.data.SharedPrefStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataModule = module {
    single<PrefStorage> { SharedPrefStorage(androidContext()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthDataRepository(get()) }
}