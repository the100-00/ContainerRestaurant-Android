package container.restaurant.android.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentResNearBinding
import container.restaurant.android.presentation.map.item.MapsViewModel
import container.restaurant.android.presentation.map.item.NearResAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class NearResFragment(val itemClick: (Int) -> Unit) : DialogFragment() {
    private val viewModel: MapsViewModel by viewModel()
    private lateinit var binding: FragmentResNearBinding
    private lateinit var nearResAdapter: NearResAdapter
    lateinit var mapsFragment: MapsFragment

    companion object {
        private var lattitude: Double = 0.0
        private var longitude: Double = 0.0
        private var radius: Int = 0
        fun newInstance(lat: Double, lon: Double, radius: Int, itemClick : (Int) -> Unit): NearResFragment {
            this.lattitude = lat
            this.longitude = lon
            this.radius = radius
            return NearResFragment(itemClick)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_res_near, container, false)
        val view = binding.root


        if(radius == 20000){
            binding.textView7.setText("반경 20km 용기낸 식당")
        }

        binding.imageButton.setOnClickListener{
            itemClick(0)
            dialog?.dismiss()
        }

        initRecyclerView()
        return view;
    }

    private fun initRecyclerView() {
        nearResAdapter = NearResAdapter()

        binding.rvNearRes.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            viewModel.fetchRes(lattitude, longitude, radius)
            viewModel.resResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                it?._embedded?.let {
                   nearResAdapter.setItems(it.restaurantNearInfoDtoList)
                }
                adapter = nearResAdapter
            })
        }
    }

}

