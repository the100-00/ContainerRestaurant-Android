package container.restaurant.android.data.response

import com.google.gson.annotations.SerializedName

data class FeedDetailResponse(
    @SerializedName("id") val feedId: Int,
    @SerializedName("ownerId") val ownerId: Int,
    @SerializedName("restaurantId") val restaurantId: Int,
    @SerializedName("ownerNickname") val ownerNickname: String,
    @SerializedName("ownerContainerLevel") val ownerContainerLevel: String,
    @SerializedName("ownerProfile") val ownerProfile: String,
    @SerializedName("restaurantName") val restaurantName: String,
    @SerializedName("category") val category: String,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String,
    @SerializedName("content") val content: String,
    @SerializedName("welcome") val isWelcome: Boolean,
    @SerializedName("difficulty") val difficulty: Int,
    @SerializedName("likeCount") val likeCount: Int,
    @SerializedName("scrapCount") val scrapCount: Int,
    @SerializedName("replyCount") val replyCount: Int,
    @SerializedName("isContainerFriendly") val isContainerFriendly: Boolean,
    @SerializedName("mainMenu") val mainMenu: List<Menu>,
    @SerializedName("subMenu") val subMenu: List<Menu>,
    @SerializedName("isLike") val isLike: Boolean,
    @SerializedName("isScraped") val isScrapped: Boolean
) {
    data class Menu(
        @SerializedName("menuName") val menuName: String,
        @SerializedName("container") val container: String
    )
}