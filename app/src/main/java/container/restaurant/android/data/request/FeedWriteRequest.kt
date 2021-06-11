package container.restaurant.android.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedWriteRequest(
    @Json(name = "restaurantCreateDto")
    val restaurantCreateDto: RestaurantCreateDto,
    @Json(name = "category")
    val category: String,
    @Json(name = "difficulty")
    val difficulty: Int,
    @Json(name = "welcome")
    val welcome: Boolean,
    @Json(name = "thumbnailImageId")
    val thumbnailImageId: Int,
    @Json(name = "content")
    val content: String,
    @Json(name = "mainMenu")
    val mainMenu: List<MainMenu>,
    @Json(name = "subMenu")
    val subMenu: List<SubMenu>
) {
    @JsonClass(generateAdapter = true)
    data class RestaurantCreateDto(
        @Json(name = "name")
        val name: String,
        @Json(name = "address")
        val address: String,
        @Json(name = "latitude")
        val latitude: Double,
        @Json(name = "longitude")
        val longitude: Double
    )

    @JsonClass(generateAdapter = true)
    data class MainMenu(
        @Json(name = "menuName")
        val menuName: String,
        @Json(name = "container")
        val container: String
    )

    @JsonClass(generateAdapter = true)
    data class SubMenu(
        @Json(name = "menuName")
        val menuName: String,
        @Json(name = "container")
        val container: String
    )
}