package container.restaurant.android.data.model

import com.google.gson.annotations.SerializedName

data class FeedResponse(
    @SerializedName("_embedded")
    val feedModel: FeedModel?,
    @SerializedName("page")
    val page: FeedPage
)

data class FeedModel(
    @SerializedName("feedPreviewDtoList")
    val feeds: List<Feed>
)

data class Feed(
    val id: Int = 0,
    val thumbnailUrl: String = "",
    val ownerNickname: String = "",
    val content: String = "",
    val likeCount: Int = 0,
    val replyCount: Int = 0
)

data class FeedPage(
    @SerializedName("totalPages")
    val totalPage: Int
)
