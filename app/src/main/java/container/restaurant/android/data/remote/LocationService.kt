package container.restaurant.android.data.remote

import com.skydoves.sandwich.ApiResponse
import container.restaurant.android.data.response.SearchLocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {
    @GET("v1/search/local.json")
    suspend fun searchPlace(
        @Query("query") query: String,
        @Query("display") display: Int? = 5,
        @Query("start") start: Int? = 1
    ): ApiResponse<SearchLocationResponse>
}