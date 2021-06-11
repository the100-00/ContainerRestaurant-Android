package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import container.restaurant.android.data.response.MyFeedResponse
import container.restaurant.android.databinding.FragmentMyScrapBinding
import container.restaurant.android.presentation.base.BaseFragment
import container.restaurant.android.presentation.my.adapter.MyFeedAdapter
import container.restaurant.android.util.hide
import container.restaurant.android.util.observe
import container.restaurant.android.util.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyScrapFragment : BaseFragment() {

    private lateinit var binding: FragmentMyScrapBinding

    private val viewModel: MyViewModel by viewModel()

    private val args: MyScrapFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyScrapBinding.inflate(inflater, container, false)
        context ?: return binding.root

        setBindItem()
        subscribeUi()

        return binding.root
    }

    private fun setBindItem() {
        binding.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
    private fun subscribeUi() {
        with(viewModel) {
            observe(viewLoading, ::loadingCheck)
            observe(getErrorMsg, ::errorDialog)

            observe(getMyScrapFeed(args.id), ::completeFeed)
        }
    }

    private fun completeFeed(myFeedResponse: MyFeedResponse) {

        val adapter = MyFeedAdapter()
        binding.rvMyFeed.adapter = adapter
        adapter.submitList(myFeedResponse.embedded?.feedPreviewDtoList)

        if(myFeedResponse.embedded?.feedPreviewDtoList.isNullOrEmpty()) {
            binding.llEmptyInfo.show()
        } else {
            binding.llEmptyInfo.hide()
        }
    }
}