package container.restaurant.android.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentResNearBinding
import container.restaurant.android.presentation.map.item.NearRestaurant
import container.restaurant.android.presentation.map.item.NearResAdapter

internal class NearResFragment : Fragment() {
    private lateinit var viewModel: MapViewModel
    private lateinit var binding: FragmentResNearBinding
    private lateinit var nearResAdapter: NearResAdapter
    private var nearRestaurant: ArrayList<NearRestaurant> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_res_near, container, false)
        val view = binding.root

        nearRestaurant.run {
            add(NearRestaurant(1, "", "abc", "ddd", "111", "21321"))
        }
        initRecyclerView()
        return view;
    }

    private fun initRecyclerView() {
        nearResAdapter = NearResAdapter()
        binding.rvNearRes.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = nearResAdapter
            nearResAdapter.setItems(nearRestaurant)
        }
    }


    companion object {
        fun newInstance(): NearResFragment = NearResFragment()

    }
}