package container.restaurant.android.data.response

import com.google.gson.annotations.SerializedName

data class SignInWithAccessTokenResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profile") val profile: String,
    @SerializedName("levelTitle") val levelTitle: String,
    @SerializedName("feedCount") val feedCount: Int,
    @SerializedName("scrapCount") val scrapCount: Int,
    @SerializedName("bookmarkedCount") val bookmarkedCount: Int,
    @SerializedName("_links") val links: Links
) {
    data class Links(
        @SerializedName("self") val self : Link.GeneralLink,
        @SerializedName("feeds") val feeds : Link.GeneralLink,
        @SerializedName("patch") val patch: Link.GeneralLink,
        @SerializedName("delete") val delete: Link.GeneralLink,
        @SerializedName("nickname-exists") val nicknameExists: Link.NickNameExistsLink,
        @SerializedName("scraps") val scraps: Link.GeneralLink,
        @SerializedName("restaurant-favorite") val restaurantFavorite: Link.GeneralLink
    ){
        sealed class Link {
            data class GeneralLink(
                @SerializedName("href") val href: String
            )
            data class NickNameExistsLink(
                @SerializedName("href") val href: String,
                @SerializedName("templated") val templated: Boolean
            ): Link()
        }
    }
}