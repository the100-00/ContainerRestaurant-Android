package container.restaurant.android.data.model

import com.google.gson.annotations.SerializedName

data class FeedCommentResponse(
    @SerializedName("_embedded")
    val data: FeedCommentModel
)

data class FeedCommentModel(
    @SerializedName("commentInfoDtoList")
    val feedComments: List<FeedComment> = emptyList()
)

data class FeedComment(
    val id: Long,
    val content: String = "",
    val isDeleted: Boolean = false,
    val likeCount: Int = 0,
    val ownerId: Long,
    val ownerNickName: String = "",
    val ownerProfile: String = "",
    val ownerLevel: Int = 0,
    val createdDate: String = "",
    val commentReply: List<FeedComment> = emptyList()
)
