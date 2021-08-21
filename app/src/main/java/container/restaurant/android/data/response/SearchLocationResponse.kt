package container.restaurant.android.data.response

import com.google.gson.annotations.SerializedName

data class SearchLocationResponse(
    @SerializedName("lastBuildDate") val lastBuildDate: String,
    @SerializedName("total") val total: Int,
    @SerializedName("start") val start: Int,
    @SerializedName("display") val display: Int,
    @SerializedName("items") val items: List<Item>
) {
    data class Item(
        @SerializedName("title") val title: String,
        @SerializedName("link") val link: String,
        @SerializedName("category") val category: String,
        @SerializedName("description") val description: String,
        @SerializedName("telephone") val telephone: String,
        @SerializedName("address") val address: String,
        @SerializedName("roadAddress") val roadAddress: String,
        @SerializedName("mapx") val mapx: String,
        @SerializedName("mapy") val mapy: String
    )
}