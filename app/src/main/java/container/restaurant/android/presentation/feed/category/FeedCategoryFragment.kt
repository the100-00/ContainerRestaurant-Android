package container.restaurant.android.presentation.feed.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.data.FeedCategory
import container.restaurant.android.data.SortingCategory
import container.restaurant.android.databinding.FragmentFeedCategoryBinding
import container.restaurant.android.presentation.feed.detail.FeedDetailActivity
import container.restaurant.android.presentation.feed.item.ContainerFeedAdapter
import container.restaurant.android.util.DataTransfer
import container.restaurant.android.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


internal class FeedCategoryFragment : Fragment() {

    private lateinit var binding: FragmentFeedCategoryBinding

    private val viewModel: FeedCategoryViewModel by viewModel {
        parametersOf(feedCategory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedCategoryBinding.inflate(layoutInflater, container, false)
            .apply {
                viewModel = this@FeedCategoryFragment.viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.getFeedList(SortingCategory.LATEST)
        }
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            isExploreFeedItemClicked.observe(viewLifecycleOwner, EventObserver{
                if(it){
                    val intent = FeedDetailActivity.getIntent(requireActivity())
                    intent.putExtra(DataTransfer.FEED_ID, selectedFeedId)
                    startActivity(intent)
                }
            })
        }
    }

    private var feedCategory: FeedCategory? = null

    companion object {
        fun newInstance(feedCategory: FeedCategory) = FeedCategoryFragment()
            .apply {
                this.feedCategory = feedCategory
            }
    }
}