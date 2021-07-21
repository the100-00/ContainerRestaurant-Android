package container.restaurant.android.data.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("email") val email : String,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("profile") val profile : String,
    @SerializedName("levelTitle") val levelTitle : String,
    @SerializedName("feedCount") val feedCount : Int,
    @SerializedName("scrapCount") val scrapCount : Int,
    @SerializedName("bookmarkedCount") val bookmarkedCount : Int
)