package container.restaurant.android.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.activity.result.contract.ActivityResultContracts.*
//import androidx.activity.registerForActivityResult
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentMapBinding
import container.restaurant.android.presentation.feed.detail.FeedDetailActivity
import container.restaurant.android.presentation.feed.write.FeedWriteActivity
import container.restaurant.android.presentation.map.item.*
import container.restaurant.android.presentation.map.item.ConResAdapter
import container.restaurant.android.presentation.map.item.FeedRestaurantViewModel
import container.restaurant.android.presentation.map.item.MapResAdapter
import container.restaurant.android.presentation.map.item.MapsViewModel
import container.restaurant.android.util.DataTransfer
import container.restaurant.android.util.EventObserver
import container.restaurant.android.util.hide
import container.restaurant.android.util.show
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
    private var Rlatitude: Double = 0.0
    private var Rlongitude: Double = 0.0
    private var radius: Int = 2000
    private var index: Int = 0
    private val locationUtils = LocationUtils()
    private val viewModel: MapsViewModel by viewModel()
    private val viewModel2: FeedRestaurantViewModel by viewModel()
    private val markers = mutableListOf<Marker>()
    private lateinit var mapResAdapter: MapResAdapter
    private lateinit var conResAdapter: ConResAdapter

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetBehavior1: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetBehavior2: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        binding.viewModel2 = viewModel2


        binding.btList.visibility = INVISIBLE //목록보기 버튼 비활성화
        //binding.btReset.visibility = INVISIBLE
        return binding.root
    }


    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()
        subscribeMyLocation()
        subscribeResResponse()

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }

        mapFragment.getMapAsync(this)

        binding.btList.setOnClickListener {//목록보기 클릭
            insertNestedFragment()
        }

        binding.btReset.setOnClickListener {//이 위치에서 검색 클릭
            markers.forEach { //마커 삭제
                it.map = null
            }
            searchAgain()
        }

        initBottomSheet() //바텀 시트 준비

        binding.bottomfirst.close.setOnClickListener {//내 위치에는 용기낸 식당이 없어요(첫번째로 뜨는 바텀시트) 닫기
            bottomSheetBehavior1.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.bottomfirst.btn0.setOnClickListener {//내 위치에는 용기낸 식당이 없어요(첫번째로 뜨는 바텀시트) 내가 먼저 용기내기
            val intent = Intent(requireContext(), FeedWriteActivity::class.java)
            startActivity(intent)
            bottomSheetBehavior1.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.bottomfirst.btn1.setOnClickListener {//내 위치에는 용기낸 식당이 없어요(첫번째로 뜨는 바텀시트) 가장 가까운 용기낸 식당 찾기
            radius = 20000;
            Toast.makeText(requireContext(), "반경 20KM에 있는 식당을 검색합니다.", Toast.LENGTH_SHORT).show()
            searchStart()
        }

        binding.bottomsecond.btn0.setOnClickListener {//가까운 곳에 용기낸 식당이 없어요(두번째로 뜨는 바텀시트) 내가 먼저 용기내기
            val intent = Intent(requireContext(), FeedWriteActivity::class.java)
            startActivity(intent)
            bottomSheetBehavior2.state = BottomSheetBehavior.STATE_COLLAPSED
        }


        binding.bottomsecond.close.setOnClickListener {//가까운 곳에 용기낸 식당이 없어요(두번째로 뜨는 바텀시트) 닫기
            bottomSheetBehavior2.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun insertNestedFragment() { //목록보기 클릭 시 호출되는 함수
        nearResFragment = NearResFragment.newInstance(Rlatitude, Rlongitude, radius){}

        nearResFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog_fullscreen)
        nearResFragment.isCancelable = true
        nearResFragment.show(childFragmentManager, "nearRes")

        //  binding.btList.visibility = INVISIBLE
        //  binding.btReset.visibility = INVISIBLE
    }

    private fun initBottomSheet() {//바텀 시트들 초기화

        view?.findViewById<ConstraintLayout>(R.id.bottomfirst)?.let {
            bottomSheetBehavior1 = BottomSheetBehavior.from(it)
        }
        bottomSheetBehavior1.state = BottomSheetBehavior.STATE_COLLAPSED

        view?.findViewById<ConstraintLayout>(R.id.bottomsecond)?.let {
            bottomSheetBehavior2 = BottomSheetBehavior.from(it)
        }
        bottomSheetBehavior2.state = BottomSheetBehavior.STATE_COLLAPSED

         view?.findViewById<ConstraintLayout>(R.id.bottomres)?.let {
            bottomSheetBehavior = BottomSheetBehavior.from(it)
        }
        bottomSheetBehavior.peekHeight = 0

    }

    private fun searchStart() { // 가장 가까운 용기낸 식당 찾기 했을 때 호출되는 함수. 반경 20km 검색
        val latlng: CameraPosition = cp
        viewModel.fetchRes(latlng.target.latitude, latlng.target.longitude, radius)
        latitude = latlng.target.latitude
        longitude = latlng.target.longitude

        Rlatitude = latitude
        Rlongitude = longitude

        index = 1
        bottomSheetBehavior1.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun searchAgain() { // 이 위치에서 검색 했을 때 호출되는 함수. 반경 2km 검색
        radius = 2000;
        val latlng: CameraPosition = cp
        viewModel.fetchRes(latlng.target.latitude, latlng.target.longitude, radius)
        latitude = latlng.target.latitude
        longitude = latlng.target.longitude

        Rlatitude = latitude
        Rlongitude = longitude

        index = 2
    }

    private fun subscribeMyLocation() { //실시간 위치 감지 함수
        locationUtils.getCurrentLocationLatLng().observe(viewLifecycleOwner, EventObserver {
            Timber.e(it.toString())
            viewModel.fetchRes(it.latitude, it.longitude, radius)
            latitude = it.latitude
            longitude = it.longitude
        })

        Rlatitude = latitude
        Rlongitude = longitude
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
    }

    private fun NRes() {
        binding.btList.visibility = INVISIBLE
        if (index == 0) {//처음 검색했을 떄 용기낸 식당이 없을 때 -> 내 위치에 용기낸 식당이 없어요(첫번째 바텀시트) 띄우기
            bottomSheetBehavior1.state = BottomSheetBehavior.STATE_EXPANDED
        } else if (index == 1) {//반경 20km 용기낸 식당을 찾지 못했을 때 -> 가까운 용기낸 식당이 없어요(두번째 바텀시트) 띄우기
            bottomSheetBehavior2.state = BottomSheetBehavior.STATE_EXPANDED
        } else if (index == 2) {//이 위치에서 검색 했을 때 용기낸 식당을 찾지 못헀을 경우
            Toast.makeText(requireContext(), "이 위치에는 용기낸 식당이 아직 없어요", Toast.LENGTH_LONG).show()
            radius = 2000;
        }
    }

    //마커 문제
    private fun createMarker(id: Long, lat: Double, lon: Double) {
        val marker = Marker()
        markers.add(marker)

        marker.position = LatLng(lat, lon)
        marker.map = naverMap
        marker.icon = OverlayImage.fromResource(R.drawable.ic_map_marker_s)
        marker.setOnClickListener { overlay ->
            markers.forEach {
                it.icon = OverlayImage.fromResource(R.drawable.ic_map_marker_s)
            }

            marker.icon = OverlayImage.fromResource(R.drawable.ic_map_marker__2_)

            bottomSheetBehavior.peekHeight = 322
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            initRecyclerView(id)
            setupContainerFeedRecycler(id)
            false
        }
    }

    private fun initRecyclerView(id : Long) {
        mapResAdapter = MapResAdapter()
        binding.bottomres.rvDetailRes.run{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            viewModel.fetchRestaurantInfo(id)
            viewModel.resInfoResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                mapResAdapter.setItems(it)
                adapter = mapResAdapter
            })
        }
    }

    private fun setupContainerFeedRecycler(id : Long) {
        conResAdapter = ConResAdapter()
        with(binding.bottomres.rvConRes) {
            layoutManager = GridLayoutManager(context ?: return, 2)
            viewModel2.fetchResFeed(id)
            viewModel2.feedResponse.observe(viewLifecycleOwner,androidx.lifecycle.Observer {
                it.feedModel?.let { it1 -> conResAdapter.setItems(it1.feeds) }
                adapter = conResAdapter
            })
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.uiSettings.isLocationButtonEnabled = true
        naverMap.uiSettings.isCompassEnabled = false
        if (::locationSource.isInitialized)
            naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        //카메라 움직일때
        naverMap.addOnCameraChangeListener { reason, animated ->
            val currentPosition: CameraPosition = naverMap.cameraPosition
            cp = currentPosition
        }
    }
/*
    class ActivityResultSampleFragment : Fragment() {
        val requestLocation: () -> Unit = registerForActivityResult(RequestPermission(), ACCESS_FINE_LOCATION
        ) { isGranted ->
            locationUtils.initLocation(requireActivity())
            naverMap.locationSource = locationSource
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        }

        private fun requestLocationAction() {
            requestLocation()
        }
    }*/

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PermissionChecker.PERMISSION_GRANTED
        )
            locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        else
            locationUtils.initLocation(requireActivity())
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults))
        {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            locationUtils.initLocation(requireActivity())
            naverMap.locationSource = locationSource
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        return
        }
        super.onRequestPermissionsResult(requestCode, permissions,grantResults)
    }

    companion object {
        fun newInstance(): MapsFragment = MapsFragment()
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}