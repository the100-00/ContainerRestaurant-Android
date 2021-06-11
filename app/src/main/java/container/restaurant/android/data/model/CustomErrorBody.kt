package container.restaurant.android.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CustomErrorBody (
    val message: String
)