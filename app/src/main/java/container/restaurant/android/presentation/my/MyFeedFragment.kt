package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import container.restaurant.android.databinding.FragmentMyFeedBinding
import container.restaurant.android.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyFeedFragment : BaseFragment() {

    private lateinit var binding: FragmentMyFeedBinding

    private val viewModel: MyViewModel by viewModel()

    private val args: MyFeedFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentMyFeedBinding.inflate(inflater, container, false)
        context ?: return binding.root

        setBindItem()
        getMyFeedList()

        return binding.root
    }

    private fun setBindItem() {
        binding.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun getMyFeedList() {
        lifecycleScope.launchWhenCreated {
            viewModel.getMyFeedList()
        }
    }
}