package container.restaurant.android.data.model

import container.restaurant.android.presentation.feed.category.FeedCategory

data class FeedDetail(
    val id: Long,
    val ownerId: Long,
    val restaurantId: Long,
    val ownerNickname: String = "",
    val restaurantName: String = "",
    val category: FeedCategory = FeedCategory.DEFAULT,
    val thumbnailUrl: String = "",
    val content: String = "",
    val welcome: Boolean = false,
    val difficulty: Int = 0,
    val likeCount: Int = 0,
    val scrapCount: Int = 0,
    val replyCount: Int = 0,
    val mainMenu: List<Menu> = emptyList(),
    val subMenu: List<Menu> = emptyList(),
    val isLike: Boolean = false,
    val isScraped: Boolean = false
)

data class Menu(
    val menuName: String = "",
    val container: String = ""
)
