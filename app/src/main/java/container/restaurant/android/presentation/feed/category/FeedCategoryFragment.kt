package container.restaurant.android.presentation.feed.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tak8997.github.domain.ContainerFeedHistory
import container.restaurant.android.databinding.FragmentFeedCategoryBinding
import container.restaurant.android.presentation.feed.item.ContainerFeedAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


internal class FeedCategoryFragment : Fragment() {

    private lateinit var binding: FragmentFeedCategoryBinding

    private val viewModel: FeedCategoryViewModel by viewModel()

    private val containerFeedAdapter = ContainerFeedAdapter()

    private var feedCategory: FeedCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedCategory = arguments?.getSerializable(KEY_FEED_CATEGORY) as? FeedCategory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFeedCategoryBinding.inflate(layoutInflater, container, false)
            .apply {
                viewModel = this@FeedCategoryFragment.viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContainerFeedRecycler()
    }

    private fun setupContainerFeedRecycler() {
        with(binding.rvContainerFeed) {
            layoutManager = GridLayoutManager(context ?: return, 2)
            adapter = containerFeedAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = layoutManager
                    if (layoutManager is GridLayoutManager) {
                        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount

                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                                // TODO : check loading status for multiple fetch
                            viewModel.fetchMore()
                        }
                    }
                }
            })
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
                ContainerFeedHistory(12, "", "닉네임", "컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 컨텐츠 ", "99", "99"),
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

    companion object {
        private const val KEY_FEED_CATEGORY = "KEY_FEED_CATEGORY"

        fun newInstance(feedCategory: FeedCategory) = FeedCategoryFragment()
            .apply {
                arguments = bundleOf(KEY_FEED_CATEGORY to feedCategory)
            }
    }
}