package container.restaurant.android.data.repository

import com.tak8997.github.domain.ResultState
import container.restaurant.android.data.response.RestaurantNearInfoDto
import container.restaurant.android.data.response.RestaurantResponse
import container.restaurant.android.data.remote.RestaurantService
import container.restaurant.android.data.safeApiCall

interface RestaurantRepository {
    suspend fun fetchResList(
        lat : Double, lon : Double, radius : Int) : ResultState<RestaurantResponse>
    suspend fun fetchResInfo(
        id: Long
    ): ResultState<RestaurantNearInfoDto>
}

internal class ResDataRepository(
    private val resService: RestaurantService
) : RestaurantRepository {

    override suspend fun fetchResList(
        lat : Double,
        lon : Double,
        radius : Int
    ): ResultState<RestaurantResponse> {
        return safeApiCall { resService.fetchResList(lat, lon, radius) }
    }

    override suspend fun fetchResInfo(id: Long): ResultState<RestaurantNearInfoDto> {
        return safeApiCall { resService.fetchResInfo(id) }
    }
}
