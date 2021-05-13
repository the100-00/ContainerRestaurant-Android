package container.restaurant.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import container.restaurant.android.di.dataModule
import container.restaurant.android.di.presentationModule
import container.restaurant.android.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class ContainerRestaurantApp : Application() {

    override fun onCreate() {
        super.onCreate()

        setupTimber()
        setupKoin()
        setupKakaoSdk()
    }

    private fun setupKakaoSdk() {
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@ContainerRestaurantApp)
            modules(listOf(presentationModule, repositoryModule, dataModule))
        }
    }
}