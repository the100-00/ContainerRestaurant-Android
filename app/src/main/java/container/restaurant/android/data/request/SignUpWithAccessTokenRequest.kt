package container.restaurant.android.data.request

import com.google.gson.annotations.SerializedName

data class SignUpWithAccessTokenRequest (
    @SerializedName("provider") val provider: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("profileId") val profileId: Int? = null,
    @SerializedName("pushToken") val pushToken:String? = null
)