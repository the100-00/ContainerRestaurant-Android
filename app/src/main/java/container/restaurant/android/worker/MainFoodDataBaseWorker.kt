package container.restaurant.android.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import container.restaurant.android.data.db.AppDatabase
import container.restaurant.android.data.db.MainFood
import kotlinx.coroutines.coroutineScope
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class MainFoodDatabaseWorker (context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters),
    KoinComponent {

    private val roomDatabase: AppDatabase by inject()

    override suspend fun doWork(): Result = coroutineScope {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        try {
            val adapter: JsonAdapter<MainFood> = moshi.adapter(MainFood::class.java)
            val fromJson = adapter.fromJson(adapter.toJson(MAIN_FOOD))!!
            roomDatabase.mainFoodDao().insertMainFood(fromJson)
            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error MainFood database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "MainFoodWorker"
        private val MAIN_FOOD = MainFood(foodName = "", bottle = "")
    }
}