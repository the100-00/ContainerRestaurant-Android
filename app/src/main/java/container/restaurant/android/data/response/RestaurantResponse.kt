package container.restaurant.android.data.response

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class RestaurantResponse(
    @SerializedName("_embedded")
    val _embedded : RestaurantNearInfoDtoList?,
    @SerializedName("_links")
    val links: Links
)

data class Links(
    val self: JsonObject
)

data class RestaurantNearInfoDtoList(
    val restaurantNearInfoDtoList: List<RestaurantNearInfoDto>
)

data class RestaurantNearInfoDto(
    val id : Long,
    val name : String,
    val address : String,
    val latitude : Double,
    val longitude : Double,
    val image_path : String,
    val difficultyAvg : Double,
    val feedCount : Int,
    val _links: JsonObject
){
    init {
        _links["self"].asJsonObject["href"].asString
        _links["image-url"].asJsonObject["href"].asString
        _links["restaurant-vanish"].asJsonObject["href"].asString
    }
}
