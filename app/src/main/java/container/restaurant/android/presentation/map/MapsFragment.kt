package container.restaurant.android.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentMapBinding
import container.restaurant.android.presentation.map.item.LocationUtils
import container.restaurant.android.presentation.map.item.MapsViewModel
import container.restaurant.android.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

internal class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private lateinit var naverMap: NaverMap
    private lateinit var naverMapOptions: NaverMapOptions
    private lateinit var locationSource: FusedLocationSource
    lateinit var nearResFragment: NearResFragment
    private lateinit var cp: CameraPosition
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var radius: Int = 2000
    private var index: Int = 0
    private val locationUtils = LocationUtils()
    lateinit var firstDialogFragment: FirstDialogFragment
    lateinit var secondDialogFragment : SecondDialogFragment
    private val viewModel : MapsViewModel by viewModel()
    private val markers = mutableListOf<Marker>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel


        binding.btList.visibility = INVISIBLE
        //binding.btReset.visibility = INVISIBLE
        return binding.root
    }

    private fun insertNestedFragment() {
        nearResFragment = NearResFragment.newInstance(latitude, longitude, radius) {
            when (it) {
                0 -> {
                }
            }
        }
        nearResFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog_fullscreen)
        nearResFragment.isCancelable = true
        nearResFragment.show(childFragmentManager, "nearRes")

        //  binding.btList.visibility = INVISIBLE
        //  binding.btReset.visibility = INVISIBLE
    }

    @SuppressLint("MissingPermission")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkPermissions()
        subscribeMyLocation()
        subscribeResResponse()

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }

        mapFragment.getMapAsync(this)

        binding.btList.setOnClickListener {
            insertNestedFragment()
        }

        binding.btReset.setOnClickListener {
            markers.forEach {
                it.map = null
            }
            searchAgain()
        }

        firstDialogFragment = FirstDialogFragment.newInstance {
            when (it) {
                0 -> Toast.makeText(requireContext(), "시작", Toast.LENGTH_SHORT).show()
                1 -> {
                    radius = 20000;
                    searchStart()
                }
            }
        }

        secondDialogFragment = SecondDialogFragment.newInstance {
            when (it) {
                1 -> {
                }
            }
        }

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

    }

    private fun searchStart() {
        //Toast.makeText(requireContext(), "이 위치에서 검색합니다.", Toast.LENGTH_SHORT).show()
        val latlng: CameraPosition = cp
        viewModel.fetchRes(latlng.target.latitude, latlng.target.longitude, radius)
        latitude = latlng.target.latitude
        longitude = latlng.target.longitude

        index = 1
    }

    private fun searchAgain() {
        val latlng: CameraPosition = cp
        viewModel.fetchRes(latlng.target.latitude, latlng.target.longitude, radius)
        latitude = latlng.target.latitude
        longitude = latlng.target.longitude

        index = 2
    }

    private fun subscribeMyLocation() {
        locationUtils.getCurrentLocationLatLng().observe(viewLifecycleOwner, EventObserver {
            Timber.e(it.toString())
            viewModel.fetchRes(it.latitude, it.longitude, radius)
            latitude = it.latitude
            longitude = it.longitude
        })
/*
        viewModel.fetchRes(37.48735456286126,126.91299694023027,1000)
        latitude = 37.48735456286126
        longitude = 126.91299694023027
        radius = 1000
*/
        //subscribeResResponse()
    }

    private fun subscribeResResponse() {
        viewModel.resResponse.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { response ->
                response?.let {
                    binding.btList.visibility = VISIBLE
                    it._embedded?.restaurantNearInfoDtoList?.forEach {
                        createMarker(it.id, it.latitude, it.longitude)
                        latitude = it.latitude
                        longitude = it.longitude
                    }
                } ?: NRes()
            })

        val cameraUpdate = CameraPosition(
            LatLng(latitude, longitude), 9.0)

        naverMapOptions = NaverMapOptions()
        naverMapOptions.camera(cameraUpdate)
    }

    private fun NRes() {
        if (index == 0) {
            firstDialogFragment.show(childFragmentManager, "bottom_dialog")
        } else if(index == 1){
            secondDialogFragment.show(childFragmentManager, "bottom_dialog")
        }
        else if(index == 2){
            Toast.makeText(requireContext(), "이 위치에는 용기낸 식당이 아직 없어요", Toast.LENGTH_LONG).show()
        }
        binding.btList.visibility = INVISIBLE
    }

    //마커 문제
    private fun createMarker(id: Long, lat: Double, lon: Double) {
        val marker = Marker()
        markers.add(marker) //마커 리스트에 저장. -> 삭제 어디서?
        marker.position = LatLng(lat, lon)
        marker.map = naverMap
        marker.icon = OverlayImage.fromResource(R.drawable.ic_map_marker_s)
        marker.setOnClickListener { overlay ->
            markers.forEach {
                it.icon = OverlayImage.fromResource(R.drawable.ic_map_marker_s)
            }

            marker.icon = OverlayImage.fromResource(R.drawable.ic_map_marker)
            // marker.icon = OverlayImage.fromResource(R.drawable.ic_map_marker)
            val resDialogFragment = ResDialogFragment.newInstance(id) {
                when (it) {

                }
            }
            resDialogFragment.show(childFragmentManager, "custom_dialog")
            false
        }

    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.uiSettings.isLocationButtonEnabled = true
        naverMap.uiSettings.isCompassEnabled = false
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        //카메라 움직일때
        naverMap.addOnCameraChangeListener { reason, animated ->
            val currentPosition: CameraPosition = naverMap.cameraPosition
            cp = currentPosition
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PermissionChecker.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        else
            locationUtils.initLocation(requireActivity())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
                /*val cameraPosition = CameraPosition(
                     LatLng(37.5666102, 126.9783881), // 대상 지점
                     17.0)
                 naverMap.cameraPosition = cameraPosition*/
            } else {
                locationUtils.initLocation(requireActivity())
                naverMap.locationSource = locationSource
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    companion object {
        fun newInstance(): MapsFragment = MapsFragment()
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}