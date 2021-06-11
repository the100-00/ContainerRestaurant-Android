package container.restaurant.android.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentResBinding

import container.restaurant.android.presentation.map.item.ConResAdapter
import container.restaurant.android.presentation.map.item.FeedRestaurantViewModel
import container.restaurant.android.presentation.map.item.MapResAdapter
import container.restaurant.android.presentation.map.item.MapsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResDialogFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {
    private val viewModel: MapsViewModel by viewModel()
    private val viewModel2: FeedRestaurantViewModel by viewModel()
    private lateinit var binding: FragmentResBinding
    private lateinit var mapResAdapter: MapResAdapter
    private lateinit var conResAdapter: ConResAdapter

    companion object {
        private var id : Long = 0
        fun newInstance(id: Long, itemClick : (Int) -> Unit) : ResDialogFragment{
            this.id = id
            return ResDialogFragment(itemClick)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_res, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        binding.viewModel2 = viewModel2

        initRecyclerView()
        setupContainerFeedRecycler()
        return binding.root;
    }

    private fun initRecyclerView() {
        mapResAdapter = MapResAdapter()

        binding.rvDetailRes.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            viewModel.fetchRestaurantInfo(ResDialogFragment.id)
            viewModel.resInfoResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                mapResAdapter.setItems(it)
                adapter = mapResAdapter
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupContainerFeedRecycler() {
        conResAdapter = ConResAdapter()
       with(binding.rvConRes) {
           layoutManager = GridLayoutManager(context ?: return, 2)
           viewModel2.fetchResFeed(ResDialogFragment.id)
           viewModel2.feedResponse.observe(viewLifecycleOwner,androidx.lifecycle.Observer {
               it.feedModel?.let { it1 -> conResAdapter.setItems(it1.feeds) }
               adapter = conResAdapter
           })
       }
    }
}
