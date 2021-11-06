package container.restaurant.android.data.response

import com.google.gson.annotations.SerializedName

data class SignUpWithAccessTokenResponse(
    @SerializedName("id") val id :Int,
    @SerializedName("token") val token: String
)