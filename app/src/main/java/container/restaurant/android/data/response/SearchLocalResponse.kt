package container.restaurant.android.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchLocalResponse(
    @Json(name = "lastBuildDate")
    val lastBuildDate: String,
    @Json(name = "total")
    val total: Int,
    @Json(name = "start")
    val start: Int,
    @Json(name = "display")
    val display: Int,
    @Json(name = "items")
    val items: List<Item>
) {
    @JsonClass(generateAdapter = true)
    data class Item(
        @Json(name = "title")
        val title: String,
        @Json(name = "link")
        val link: String,
        @Json(name = "category")
        val category: String,
        @Json(name = "description")
        val description: String,
        @Json(name = "telephone")
        val telephone: String,
        @Json(name = "address")
        val address: String,
        @Json(name = "roadAddress")
        val roadAddress: String,
        @Json(name = "mapx")
        val mapx: String,
        @Json(name = "mapy")
        val mapy: String
    )
}