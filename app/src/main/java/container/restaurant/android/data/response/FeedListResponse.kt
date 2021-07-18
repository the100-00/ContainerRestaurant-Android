package container.restaurant.android.data.response

import com.google.gson.annotations.SerializedName

data class FeedListResponse (
    @SerializedName("_embedded") val embedded : FeedPreviewDtoList,
    @SerializedName("page") val page : Page
) {
    data class FeedPreviewDtoList(val feedPreviewDtoList: List<FeedPreviewDto>){
        data class FeedPreviewDto(
            @SerializedName("id") val id : Int,
            @SerializedName("thumbnailUrl") val thumbnailUrl : String,
            @SerializedName("ownerNickname") val ownerNickname : String,
            @SerializedName("content") val content : String,
            @SerializedName("likeCount") val likeCount : Int,
            @SerializedName("replyCount") val replyCount : Int,
            @SerializedName("isContainerFriendly") val isContainerFriendly : Boolean
        )
    }
    data class Page(
        @SerializedName("size") val size : Int,
        @SerializedName("totalElements") val totalElements : Int,
        @SerializedName("totalPages") val totalPages : Int,
        @SerializedName("number") val number : Int
    )
}