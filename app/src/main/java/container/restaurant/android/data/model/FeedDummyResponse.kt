package container.restaurant.android.data.model

import com.google.gson.annotations.SerializedName

data class FeedDummyResponse(
    @SerializedName("_links")
    val links: Link? = null
)

class Link