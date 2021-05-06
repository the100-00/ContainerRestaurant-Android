package container.restaurant.android

import android.app.Application
import container.restaurant.android.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ContainerRestaurantApp : Application() {

    override fun onCreate() {
        super.onCreate()

        setupTimber()
        setupKoin()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@ContainerRestaurantApp)
            modules(listOf(mainModule))
        }
    }
}