package container.restaurant.android.data.response

import com.google.gson.annotations.SerializedName

data class AllCourageResponse(
    @SerializedName("latestWriters") val latestWriters: List<CourageResponse>,
    @SerializedName("topWriters") val topWriters: List<CourageResponse>,
    @SerializedName("writerCount") val writerCount: Int,
    @SerializedName("feedCount") val feedCount: Int
) {
    data class CourageResponse(
        @SerializedName("id") val userId: Int,
        @SerializedName("levelTitle") val levelTitle: String,
        @SerializedName("nickname") val nickname: String,
        @SerializedName("profile") val profileUrl: String
    )
}
