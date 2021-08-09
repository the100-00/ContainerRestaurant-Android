package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import container.restaurant.android.databinding.FragmentMyScrapBinding
import container.restaurant.android.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyScrapFragment : BaseFragment() {

    private lateinit var binding: FragmentMyScrapBinding

    private val viewModel: MyViewModel by viewModel()

    private val args: MyScrapFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMyScrapBinding.inflate(inflater, container, false).apply {
            this.viewModel = this@MyScrapFragment.viewModel
            this.lifecycleOwner = this@MyScrapFragment
        }
        context ?: return binding.root

        setBindItem()
        getMyScrapFeedList()

        return binding.root
    }

    private fun setBindItem() {
        binding.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun getMyScrapFeedList(){
        lifecycleScope.launchWhenCreated {
            viewModel.getMyScrapFeedList()
        }
    }
}