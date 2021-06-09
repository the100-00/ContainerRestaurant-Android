package container.restaurant.android.presentation.map.item

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import container.restaurant.android.util.Event
import timber.log.Timber
import java.util.*

open class LocationUtils {

    private val REQUEST_CHECK_SETTINGS = 0x1

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2

    private lateinit var activity: Activity

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val mLocationRequest = LocationRequest()

    private lateinit var mSettingsClient: SettingsClient
    private var mLocationCallback: LocationCallback? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null

    private val currentLocationAddress = MutableLiveData<Event<String>>()
    private val currentLocationLatLng = MutableLiveData<Event<Location>>()

    fun initLocation(activity: Activity) {
        this.activity = activity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        mSettingsClient = LocationServices.getSettingsClient(activity)

        createLocationCallback()
        buildLocationSettingsRequest()
        startLocationUpdates()
    }

    private fun buildLocationSettingsRequest() {
        mLocationRequest.apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        mLocationSettingsRequest = builder.build()
    }

    @SuppressLint("MissingPermission")
    fun checkLastLocation() {
        Timber.e("checkLastLocation")
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // Location 을 한번이라도 끄게 되면 이전 Location 정보는 다 지워지기에 여기로 호출된다.
                if (location == null || location.latitude.toInt() == 0) {
                    startLocationUpdates() // 새로 위치 정보를 가져온다.
                } else { // Location 을 끄지 않고 있을 경우 마지막 위치 값을 가져와서 처리하는 부분
                    val geoCoder = Geocoder(activity, Locale.getDefault())
                    //(activity.application as MainApplication).location.value = location
                    for(address in geoCoder.getFromLocation(location.latitude, location.longitude, 7)) {
                        if(address.thoroughfare != null) {
                            Timber.e(address.thoroughfare)
                            currentLocationAddress.value = Event(address.thoroughfare)
                            currentLocationLatLng.value = Event(location)
                            break
                        }
                    }
                }
            }.addOnFailureListener { e ->
                    Timber.e("onFailure : %s", e.localizedMessage)
            }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest!!)
            .addOnSuccessListener(activity) {
                Timber.e("All location settings are satisfied.")
                try {
                    fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback!!, Looper.getMainLooper())
                    stopLocationUpdate()
                    checkLastLocation()
                } catch (e: Exception) {
                    Timber.e("Error : %s", e.localizedMessage)
                }
            }
            .addOnFailureListener(activity, OnFailureListener { e ->
                when ((e as ApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Timber.e("Location settings are not satisfied. Attempting to upgrade location settings ")
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            val rae = e as ResolvableApiException
                            rae.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS)
                        } catch (sie: IntentSender.SendIntentException) {
                            Timber.e("PendingIntent unable to execute request.")
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        val errorMessage = "Location settings are inadequate, and cannot be " +
                                "fixed here. Fix in Settings."
                        Timber.e(errorMessage)

                        //mRequestingLocationUpdates = false
                    }
                }
                //updateUI()
            })
    }

    fun stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(mLocationCallback)
    }

    /**
     * Creates a callback for receiving location events.
     */
    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (loc in locationResult.locations){
                    // Update UI with location data
                    //location.value = loc
                    Timber.e("Location Log : ${loc.latitude} , ${loc.longitude}")
                    //(activity.application as MainApplication).location.value = loc
                }
            }
        }
    }

    fun getCurrentLocationAddress(): LiveData<Event<String>> = currentLocationAddress
    fun getCurrentLocationLatLng(): LiveData<Event<Location>> = currentLocationLatLng
}