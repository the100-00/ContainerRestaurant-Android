package container.restaurant.android.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageUploadResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "url")
    val url: String,
    @Json(name = "_links")
    val links: Links
) {
    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "self")
        val self: Self,
        @Json(name = "image_url")
        val imageUrl: ImageUrl
    ) {
        @JsonClass(generateAdapter = true)
        data class Self(
            @Json(name = "href")
            val href: String
        )

        @JsonClass(generateAdapter = true)
        data class ImageUrl(
            @Json(name = "href")
            val href: String
        )
    }
}