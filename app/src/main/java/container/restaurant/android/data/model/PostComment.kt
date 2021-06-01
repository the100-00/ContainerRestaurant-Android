package container.restaurant.android.data.model

data class PostComment(
    val content: String,
    val upperReplyId: Long? = null
)