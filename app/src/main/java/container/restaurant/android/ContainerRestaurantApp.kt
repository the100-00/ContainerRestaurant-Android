package container.restaurant.android

import android.app.Application
import container.restaurant.android.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ContainerRestaurantApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ContainerRestaurantApp)
            modules(listOf(mainModule))
        }
    }
}