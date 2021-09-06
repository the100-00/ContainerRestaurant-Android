package container.restaurant.android.presentation.feed.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import container.restaurant.android.databinding.FragmentFeedDetailInfoBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class FeedDetailInfoFragment : Fragment() {
    private lateinit var binding: FragmentFeedDetailInfoBinding
    private val viewModel: FeedDetailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedDetailInfoBinding.inflate(inflater, container, false)
            .apply {
                this.viewModel = this@FeedDetailInfoFragment.viewModel
                this.lifecycleOwner = this@FeedDetailInfoFragment
            }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}