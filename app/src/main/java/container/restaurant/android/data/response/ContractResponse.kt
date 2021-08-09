package container.restaurant.android.data.response

import com.google.gson.annotations.SerializedName

data class ContractResponse(
    @SerializedName("_embedded") val embedded: ContractInfoDTOList
) {
    data class ContractInfoDTOList(
        @SerializedName("contractInfoDtoList") val contractInfoDTOList: List<ContractInfoDTO>
        ) {
        data class ContractInfoDTO(
            @SerializedName("title") val title: String,
            @SerializedName("article") val article: String
        )
    }
}