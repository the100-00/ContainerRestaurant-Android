package container.restaurant.android.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "email")
    val email: String,
    @Json(name = "nickname")
    val nickname: String,
    @Json(name = "profile")
    val profile: String,
    @Json(name = "level")
    val level: Int,
    @Json(name = "feedCount")
    val feedCount: Int,
    @Json(name = "scrapCount")
    val scrapCount: Int,
    @Json(name = "bookmarkedCount")
    val bookmarkedCount: Int,
    @Json(name = "_links")
    val links: Links
) {
    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "self")
        val self: Self,
        @Json(name = "feeds")
        val feeds: Feeds,
        @Json(name = "patch")
        val patch: Patch,
        @Json(name = "delete")
        val delete: Delete,
        @Json(name = "nickname-exists")
        val nicknameExists: NicknameExists,
        @Json(name = "scraps")
        val scraps: Scraps,
        @Json(name = "restaurant-favorite")
        val restaurantFavorite: RestaurantFavorite
    ) {
        @JsonClass(generateAdapter = true)
        data class Self(
            @Json(name = "href")
            val href: String
        )

        @JsonClass(generateAdapter = true)
        data class Feeds(
            @Json(name = "href")
            val href: String
        )

        @JsonClass(generateAdapter = true)
        data class Patch(
            @Json(name = "href")
            val href: String
        )

        @JsonClass(generateAdapter = true)
        data class Delete(
            @Json(name = "href")
            val href: String
        )

        @JsonClass(generateAdapter = true)
        data class NicknameExists(
            @Json(name = "href")
            val href: String,
            @Json(name = "templated")
            val templated: Boolean
        )

        @JsonClass(generateAdapter = true)
        data class Scraps(
            @Json(name = "href")
            val href: String
        )

        @JsonClass(generateAdapter = true)
        data class RestaurantFavorite(
            @Json(name = "href")
            val href: String
        )
    }
}