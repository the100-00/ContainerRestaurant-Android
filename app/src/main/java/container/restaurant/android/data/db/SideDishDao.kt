package container.restaurant.android.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SideDishDao {

    @Query("SELECT * FROM SideDish")
    fun getSideDishList(): Flow<List<SideDish>>

    @Query("SELECT COUNT(*) FROM SideDish")
    suspend fun getSideDishCount(): Int

    @Update
    fun updateSideDish(sideDish: List<SideDish>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSideDish(sideDish: SideDish)

    @Query("DELETE FROM SideDish WHERE id >= 2")
    suspend fun removeExceptFirst()
    @Query("UPDATE SideDish SET quantity = '', bottle=''")
    suspend fun updateFirstData()
    @Transaction
    suspend fun initSideDishData() {
        removeExceptFirst()
        updateFirstData()
    }
}