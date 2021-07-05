package container.restaurant.android.data.request

import com.google.gson.annotations.SerializedName

data class signInWithAccessTokenRequest(
    @SerializedName("provider") val provider: String,
    @SerializedName("accessToken") val accessToken: String
)
