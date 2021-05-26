package container.restaurant.android.presentation.map

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tak8997.github.domain.ContainerFeedHistory
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentResBinding
import container.restaurant.android.presentation.feed.item.ContainerFeedAdapter
import container.restaurant.android.presentation.map.item.ContainerRestaurant
import container.restaurant.android.presentation.map.item.MapResAdapter


class ResDialogFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {
    private lateinit var viewModel: MapViewModel
    private lateinit var binding: FragmentResBinding
    private lateinit var mapResAdapter: MapResAdapter
    private var containerRestaurant: ArrayList<ContainerRestaurant> = ArrayList()

    private val containerFeedAdapter by lazy {
        ContainerFeedAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_res, container, false)
        val view = binding.root

        containerRestaurant.run {
            add(ContainerRestaurant(1, "", "용기낸 식당", "#맛있다", "2.1", "3회"))
        }
        initRecyclerView()
        setupContainerFeedRecycler()
        return view;
    }

    private fun initRecyclerView() {
        mapResAdapter = MapResAdapter()
        binding.rvDetailRes.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mapResAdapter
            mapResAdapter.setItems(containerRestaurant)
        }
    }

    private fun setupContainerFeedRecycler() {
        with(binding.rvConRes) {
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(context ?: return, 2)
            adapter = containerFeedAdapter
        }
        // test dummy
        containerFeedAdapter.submitList(
            listOf(
                ContainerFeedHistory(
                    1,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                ),
                ContainerFeedHistory(
                    2,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                ),
                ContainerFeedHistory(
                    3,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                ),
                ContainerFeedHistory(
                    4,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                ),
                ContainerFeedHistory(
                    5,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                ),
                ContainerFeedHistory(
                    6,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                ),
                ContainerFeedHistory(
                    7,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                ),
                ContainerFeedHistory(
                    8,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                ),
                ContainerFeedHistory(
                    9,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                ),
                ContainerFeedHistory(
                    10,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                ),
                ContainerFeedHistory(
                    11,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                ),
                ContainerFeedHistory(
                    12,
                    "",
                    "닉네임",
                    "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ",
                    "99",
                    "99"
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}