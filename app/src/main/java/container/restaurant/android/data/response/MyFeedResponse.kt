package container.restaurant.android.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyFeedResponse(
    @Json(name = "_embedded")
    val embedded: Embedded?,
    @Json(name = "_links")
    val links: Links,
    @Json(name = "page")
    val page: Page
) {
    @JsonClass(generateAdapter = true)
    data class Embedded(
        @Json(name = "feedPreviewDtoList")
        val feedPreviewDtoList: List<FeedPreviewDto>
    ) {
        @JsonClass(generateAdapter = true)
        data class FeedPreviewDto(
            @Json(name = "id")
            val id: Int,
            @Json(name = "thumbnailUrl")
            val thumbnailUrl: String,
            @Json(name = "ownerNickname")
            val ownerNickname: String,
            @Json(name = "content")
            val content: String?,
            @Json(name = "likeCount")
            val likeCount: Int,
            @Json(name = "replyCount")
            val replyCount: Int,
            @Json(name = "_links")
            val links: Links
        ) {
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
    }

    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "first")
        val first: First?,
        @Json(name = "self")
        val self: Self?,
        @Json(name = "next")
        val next: Next?,
        @Json(name = "last")
        val last: Last?,
        @Json(name = "create")
        val create: Create?,
        @Json(name = "category-list")
        val categoryList: CategoryList?
    ) {
        @JsonClass(generateAdapter = true)
        data class First(
            @Json(name = "href")
            val href: String
        )

        @JsonClass(generateAdapter = true)
        data class Self(
            @Json(name = "href")
            val href: String
        )

        @JsonClass(generateAdapter = true)
        data class Next(
            @Json(name = "href")
            val href: String
        )

        @JsonClass(generateAdapter = true)
        data class Last(
            @Json(name = "href")
            val href: String
        )

        @JsonClass(generateAdapter = true)
        data class Create(
            @Json(name = "href")
            val href: String
        )

        @JsonClass(generateAdapter = true)
        data class CategoryList(
            @Json(name = "href")
            val href: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Page(
        @Json(name = "size")
        val size: Int,
        @Json(name = "totalElements")
        val totalElements: Int,
        @Json(name = "totalPages")
        val totalPages: Int,
        @Json(name = "number")
        val number: Int
    )
}