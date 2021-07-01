package container.restaurant.android.data.remote

import container.restaurant.android.data.response.RestaurantNearInfoDto
import container.restaurant.android.data.response.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface RestaurantService {

    @GET("api/restaurant/{lat}/{lon}/{radius}")
    suspend fun fetchResList(
        @Path("lat") lat: Double,
        @Path("lon") lon : Double,
        @Path("radius") radius : Int
    ): RestaurantResponse

    @GET("api/restaurant/{id}")
    suspend fun fetchResInfo(
        @Path("id") id : Long
    ): RestaurantNearInfoDto
}