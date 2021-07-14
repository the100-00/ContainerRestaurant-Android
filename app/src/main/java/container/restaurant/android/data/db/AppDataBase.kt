package container.restaurant.android.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import container.restaurant.android.DATABASE_VERSION
import container.restaurant.android.data.db.dao.MainFoodDao
import container.restaurant.android.data.db.dao.SideDishDao
import container.restaurant.android.data.db.entity.MainFood
import container.restaurant.android.data.db.entity.SideDish


@Database(entities = [MainFood::class, SideDish::class], version = DATABASE_VERSION, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mainFoodDao(): MainFoodDao
    abstract fun sideDishDao(): SideDishDao
}