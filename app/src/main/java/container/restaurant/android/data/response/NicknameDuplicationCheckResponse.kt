package container.restaurant.android.data.response

import com.google.gson.annotations.SerializedName

data class NicknameDuplicationCheckResponse(
    @SerializedName("nickname") var nickname:String? = null,
    @SerializedName("exists") var exists:Boolean? = null,
    @SerializedName("_links") val links: Links
){
    data class Links(
        @SerializedName("self") val self : Link
    ){
        data class Link(
            @SerializedName("href") val href: String
        )
    }
}