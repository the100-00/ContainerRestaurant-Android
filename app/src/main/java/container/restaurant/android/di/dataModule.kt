package container.restaurant.android.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import container.restaurant.android.DATABASE_NAME
import container.restaurant.android.data.AuthDataRepository
import container.restaurant.android.data.AuthRepository
import container.restaurant.android.data.PrefStorage
import container.restaurant.android.data.SharedPrefStorage
import container.restaurant.android.data.db.AppDatabase
import container.restaurant.android.data.repository.FeedDataRepository
import container.restaurant.android.data.repository.FeedRepository
import container.restaurant.android.data.repository.FeedWriteRepository
import container.restaurant.android.data.repository.MyRepository
import container.restaurant.android.worker.MainFoodDatabaseWorker
import container.restaurant.android.worker.SideDishDatabaseWorker
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataModule = module {
    single<PrefStorage> { SharedPrefStorage(androidContext()) }
    single<AuthRepository> { AuthDataRepository(get()) }
    single<FeedRepository> { FeedDataRepository(get()) }
    single<FeedWriteRepository> { FeedWriteRepository(get(), get()) }
    single<MyRepository> { MyRepository(get())  }
}

val roomDBModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, DATABASE_NAME).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val mainFoodRequest = OneTimeWorkRequestBuilder<MainFoodDatabaseWorker>().build()
                val sideDishRequest = OneTimeWorkRequestBuilder<SideDishDatabaseWorker>().build()
                WorkManager.getInstance(androidContext()).beginWith(mainFoodRequest).then(sideDishRequest).enqueue()
            }
        }).build()
    }
    single { get<AppDatabase>().mainFoodDao() }
    single { get<AppDatabase>().sideDishDao() }
}