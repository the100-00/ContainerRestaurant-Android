package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import container.restaurant.android.databinding.FragmentFavoriteBinding
import container.restaurant.android.presentation.base.BaseFragment
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private val viewModel: MyViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
            .apply {
                this.viewModel = this@FavoriteFragment.viewModel
                this.lifecycleOwner = this@FavoriteFragment
            }
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
        }
    }
}