package container.restaurant.android.presentation.map


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentMapBinding

internal class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    lateinit var nearResFragment : NearResFragment
 //   private val viewModel: MapViewModel by viewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun insertNestedFragment() {
        nearResFragment = NearResFragment.newInstance()
        childFragmentManager.beginTransaction().replace(R.id.bt_list, nearResFragment)
            .commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
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
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.uiSettings.isLocationButtonEnabled = true
        val marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
        marker.map = naverMap
        marker.icon = OverlayImage.fromResource(R.drawable.ic_map_marker)
        marker.setOnClickListener { overlay ->
                val resDialogFragment: ResDialogFragment = ResDialogFragment {
                    when (it) {
                    }
                }
                resDialogFragment.show(childFragmentManager, "custom_dialog")
            true
        }
           naverMap.locationSource = locationSource
           naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }

    companion object {
        fun newInstance(): MapsFragment = MapsFragment()
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}