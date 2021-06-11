package container.restaurant.android.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateUserRequest (
    @Json(name = "nickname")
    val nickname: String,
    @Json(name = "profile")
    val profile: String,
    @Json(name = "pushToken")
    val pushToken: String? = null
)