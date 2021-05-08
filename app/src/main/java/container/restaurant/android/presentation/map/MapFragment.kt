package container.restaurant.android.presentation.map


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.MapFragment
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentMapBinding


internal class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var naverMap: NaverMap
//    private lateinit var locationSource: FusedLocationSource
//    private lateinit var mapView: MapView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = FragmentMapBinding.inflate(layoutInflater, container, false)

       val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
       
        return binding.root
    }

    override fun onMapReady(naverMap: NaverMap) {
        Log.d("test log","onmapready 호출")
        this.naverMap = naverMap

        val cameraPosition = CameraPosition(
            LatLng(33.38, 126.55),  // 임의 위치 지정
            9.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition
    }

    companion object {
        fun newInstance(): MapFragment = MapFragment()
        //  private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.map_view)

        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }*/
}