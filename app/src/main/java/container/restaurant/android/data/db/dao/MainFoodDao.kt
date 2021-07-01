package container.restaurant.android.data.db.dao

import androidx.room.*
import container.restaurant.android.data.db.entity.MainFood
import kotlinx.coroutines.flow.Flow

@Dao
interface MainFoodDao {

    @Query("SELECT * FROM MainFood")
    fun getMainFoodList(): Flow<List<MainFood>>

    @Query("SELECT COUNT(*) FROM MainFood")
    suspend fun getMainFoodCount(): Int

    @Update
    fun updateMainFoodList(mainFood: List<MainFood>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMainFood(mainFood: MainFood)

    @Query("DELETE FROM MainFood WHERE id >= 2")
    suspend fun removeExceptFirst()
    @Query("UPDATE MainFood SET foodName = '', bottle = ''")
    suspend fun updateFirstData()
    @Transaction
    suspend fun initMainFoodData() {
        removeExceptFirst()
        updateFirstData()
    }
}