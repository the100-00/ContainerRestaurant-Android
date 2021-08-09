package container.restaurant.android.data.request

import com.google.gson.annotations.SerializedName

class UpdateProfileRequest(
    @SerializedName("nickname") val nickname: String? = null,
    @SerializedName("profileId") val profileId: Int? = null,
    @SerializedName("pushToken") val pushToken: String? = null
)