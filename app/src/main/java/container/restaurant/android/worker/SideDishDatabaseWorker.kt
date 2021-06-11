package container.restaurant.android.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import container.restaurant.android.data.db.AppDatabase
import container.restaurant.android.data.db.SideDish
import kotlinx.coroutines.coroutineScope
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class SideDishDatabaseWorker (context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters),
    KoinComponent {

    private val roomDatabase: AppDatabase by inject()

    override suspend fun doWork(): Result = coroutineScope {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        try {
            val adapter: JsonAdapter<SideDish> = moshi.adapter(SideDish::class.java)
            val fromJson = adapter.fromJson(adapter.toJson(SIDE_DISH))!!
            roomDatabase.sideDishDao().insertSideDish(fromJson)
            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error SideDish database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SideDishWorker"
        private val SIDE_DISH = SideDish(quantity = "",bottle = "")
    }
}