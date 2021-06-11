package container.restaurant.android.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyFavoriteResponse(
    @Json(name = "_embedded")
    val embedded: Embedded?,
    @Json(name = "_links")
    val links: Links
) {
    @JsonClass(generateAdapter = true)
    data class Embedded(
        @Json(name = "restaurantFavoriteDtoList")
        val restaurantFavoriteDtoList: List<RestaurantFavoriteDto>
    ) {
        @JsonClass(generateAdapter = true)
        data class RestaurantFavoriteDto(
            @Json(name = "id")
            val id: Int,
            @Json(name = "createDate")
            val createDate: String,
            @Json(name = "restaurant")
            val restaurant: Restaurant,
            @Json(name = "_links")
            val links: Links
        ) {
            @JsonClass(generateAdapter = true)
            data class Restaurant(
                @Json(name = "id")
                val id: Int,
                @Json(name = "name")
                val name: String,
                @Json(name = "address")
                val address: String,
                @Json(name = "latitude")
                val latitude: Double,
                @Json(name = "longitude")
                val longitude: Double,
                @Json(name = "image_path")
                val imagePath: String,
                @Json(name = "feedCount")
                val feedCount: Int,
                @Json(name = "difficultyAvg")
                val difficultyAvg: Double
            )

            @JsonClass(generateAdapter = true)
            data class Links(
                @Json(name = "cancel-favorite")
                val cancelFavorite: CancelFavorite,
                @Json(name = "restaurant-info")
                val restaurantInfo: RestaurantInfo
            ) {
                @JsonClass(generateAdapter = true)
                data class CancelFavorite(
                    @Json(name = "href")
                    val href: String
                )

                @JsonClass(generateAdapter = true)
                data class RestaurantInfo(
                    @Json(name = "href")
                    val href: String
                )
            }
        }
    }

    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "self")
        val self: Self
    ) {
        @JsonClass(generateAdapter = true)
        data class Self(
            @Json(name = "href")
            val href: String
        )
    }
}