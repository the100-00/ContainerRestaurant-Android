package container.restaurant.android.presentation.feed.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import container.restaurant.android.databinding.FragmentFeedDetailContentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FeedDetailContentFragment : Fragment() {
    private lateinit var binding: FragmentFeedDetailContentBinding
    private val viewModel: FeedDetailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedDetailContentBinding.inflate(inflater, container, false)
            .apply {
                this.viewModel = this@FeedDetailContentFragment.viewModel
                this.lifecycleOwner = this@FeedDetailContentFragment
            }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}
