package container.restaurant.android.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MainFood")
data class MainFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var foodName: String,
    var bottle: String
)