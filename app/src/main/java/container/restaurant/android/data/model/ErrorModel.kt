package container.restaurant.android.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorModel(
    @Json(name = "timestamp")
    val timestamp: String,
    @Json(name = "status")
    val status: Int,
    @Json(name = "error")
    val error: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "path")
    val path: String
)