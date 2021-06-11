package container.restaurant.android.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SideDish")
data class SideDish (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var quantity: String,
    var bottle: String
)