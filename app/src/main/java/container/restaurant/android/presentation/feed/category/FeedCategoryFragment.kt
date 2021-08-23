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
import container.restaurant.android.presentation.feed.item.ContainerFeedAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


internal class FeedCategoryFragment : Fragment() {

    private lateinit var binding: FragmentFeedCategoryBinding

    private val containerFeedAdapter = ContainerFeedAdapter()

    private var feedCategory: FeedCategory? = null

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
//        with(viewModel) {
//            feeds.observe(viewLifecycleOwner, Observer {
//                if (page == 1) {
//                    containerFeedAdapter.setItems(it)
//                } else {
//                    containerFeedAdapter.addItems(it)
//                }
//            })
//            errorToast.observe(viewLifecycleOwner, Observer {
//                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//            })
//        }

//        setupContainerFeedRecycler()
    }

    private fun setupContainerFeedRecycler() {
//        with(binding.rvContainerFeed) {
//            adapter = containerFeedAdapter
//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    val layoutManager = layoutManager
//                    if (layoutManager is GridLayoutManager) {
//                        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//                        val visibleItemCount = layoutManager.childCount
//                        val totalItemCount = layoutManager.itemCount
//
//                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
//                            && firstVisibleItemPosition >= 0
//                            && viewModel.loading.value == false
//                            && page < viewModel.lastPage.value ?: 0
//                        ) {
//                            viewModel.fetchMore(++page)
//                        }
//                    }
//                }
//            })
//        }
    }

    companion object {
        var page = 1

        fun newInstance(feedCategory: FeedCategory) = FeedCategoryFragment()
            .apply {
                this.feedCategory = feedCategory
            }
    }
}