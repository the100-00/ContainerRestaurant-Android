package container.restaurant.android.data.request

import com.google.gson.annotations.SerializedName

data class SignInWithAccessTokenRequest(
    @SerializedName("provider") val provider: String,
    @SerializedName("accessToken") val accessToken: String
)
