package container.restaurant.android.data.response

import com.google.gson.annotations.SerializedName

data class BannersInfoResponse(
    @SerializedName("_embedded") val embedded : BannerInfoDtoList
) {
    data class BannerInfoDtoList(
        @SerializedName("bannerInfoDtoList") val bannerInfoDtoList: List<BannerInfoDto>
        ){
        data class BannerInfoDto(
            @SerializedName("title") val title: String,
            @SerializedName("bannerURL") val bannerUrl: String,
            @SerializedName("contentURL") val contentUrl: String,
            @SerializedName("additionalURL") val additionalUrl: String
        )
    }
}