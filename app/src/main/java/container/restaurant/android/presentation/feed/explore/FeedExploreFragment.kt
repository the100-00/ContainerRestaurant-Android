package container.restaurant.android.presentation.feed.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import container.restaurant.android.data.FeedCategory
import container.restaurant.android.databinding.FragmentFeedExploreBinding
import container.restaurant.android.databinding.TabFeedCategoryBinding
import container.restaurant.android.presentation.feed.category.FeedCategoryFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FeedExploreFragment : Fragment() {

    private val viewModel: FeedExploreViewModel by viewModel()

    private val feedAdapter: FragmentStateAdapter by lazy {
        object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int
                = FeedCategory.values().size

            override fun createFragment(position: Int): Fragment
                = FeedCategoryFragment.newInstance(FeedCategory.values()[position])
        }
    }

    private lateinit var binding: FragmentFeedExploreBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFeedExploreBinding.inflate(layoutInflater, container, false)
            .apply {
                this.viewModel = this@FeedExploreFragment.viewModel
                this.lifecycleOwner = this@FeedExploreFragment
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFeedPager()
    }

    private fun setupFeedPager() {
        binding.pagerFeed.adapter = feedAdapter

        // tabLayout 과 viewPager 를 연결
        TabLayoutMediator(binding.tabLayoutFeedType, binding.pagerFeed) { tab, pos ->
            val binding = TabFeedCategoryBinding.inflate(layoutInflater, null, false)
                .apply {
                    item = viewModel.feedCategories[pos]
                    lifecycleOwner = this@FeedExploreFragment
                }
            tab.customView = binding.root
        }.attach()
    }
    
    companion object {
        fun newInstance(): FeedExploreFragment = FeedExploreFragment()
    }
}