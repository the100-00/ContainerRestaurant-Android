package container.restaurant.android.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.tak8997.github.domain.BannerContent
import com.tak8997.github.domain.ContainerFeedHistory
import container.restaurant.android.databinding.FragmentHomeBinding
import container.restaurant.android.presentation.home.item.BannerAdapter
import container.restaurant.android.presentation.home.item.ContainerFeedAdapter

internal class HomeFragment : Fragment() {

    private val bannerAdapter by lazy {
        BannerAdapter()
    }

    private val containerFeedAdapter by lazy {
        ContainerFeedAdapter()
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBannerPager()
        setupContainerFeedRecycler()
    }

    private fun setupContainerFeedRecycler() {
        with(binding.rvContainerFeedHistory) {
            layoutManager = GridLayoutManager(context ?: return, 2)
            adapter = containerFeedAdapter
        }
        // test dummy
        containerFeedAdapter.submitList(
            listOf(
                ContainerFeedHistory(1, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
                ContainerFeedHistory(2, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
                ContainerFeedHistory(3, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
                ContainerFeedHistory(4, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
                ContainerFeedHistory(5, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
                ContainerFeedHistory(6, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
                ContainerFeedHistory(7, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
                ContainerFeedHistory(8, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
                ContainerFeedHistory(9, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
                ContainerFeedHistory(10, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
                ContainerFeedHistory(11, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
                ContainerFeedHistory(12, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99")
            )
        )
    }

    private fun setupBannerPager() {
        binding.pagerIntroBanner.adapter = bannerAdapter
        TabLayoutMediator(binding.tablayoutIndicator, binding.pagerIntroBanner) { _, _ ->
        }.attach()
        // test dummy
        bannerAdapter.addItems(
            listOf(
                BannerContent("this is title", "this is content"),
                BannerContent("this is title", "this is content"),
                BannerContent("this is title", "this is content")
            )
        )
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}