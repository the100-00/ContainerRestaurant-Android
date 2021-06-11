package container.restaurant.android.util

import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import container.restaurant.android.data.model.CustomErrorBody

object ErrorResponseMapper : ApiErrorModelMapper<CustomErrorBody> {

    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): CustomErrorBody {

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(CustomErrorBody::class.java)
        return try {
            jsonAdapter.fromJson(apiErrorResponse.errorBody!!.source())!!
        } catch (e: Exception) {
            CustomErrorBody(e.toString())
        }
    }
}