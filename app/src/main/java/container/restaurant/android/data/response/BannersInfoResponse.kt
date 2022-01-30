package container.restaurant.android.data.response

import com.google.gson.annotations.SerializedName

data class HomeInfoResponse(
    @SerializedName("loginId") val loginId: Int,
    @SerializedName("myContainer") val userFeedCount: Int,
    @SerializedName("totalContainer") val totalFeedCount: Int,
    @SerializedName("myLevelTitle") val userLevelTitle: String,
    @SerializedName("myProfile") val userProfileUrl: String,
    @SerializedName("phrase") val phrase: String,
    @SerializedName("latestWriterProfile") val latestWriterProfileList: List<WriterProfile>,
    @SerializedName("banners") val bannerList: List<Banner>
) {
    data class WriterProfile(
        @SerializedName("id") val userId: Int,
        @SerializedName("levelTitle") val levelTitle: String,
        @SerializedName("nickname") val nickname: String,
        @SerializedName("profile") val profileImgUrl: String
    )
    data class Banner(
        @SerializedName("bannerURL") val bannerUrl: String,
        @SerializedName("contentURL") val contentUrl: String,
        @SerializedName("additionalURL") val additionalUrl: String
    )
}