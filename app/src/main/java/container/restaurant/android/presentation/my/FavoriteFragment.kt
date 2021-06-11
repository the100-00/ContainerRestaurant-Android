package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import container.restaurant.android.data.response.MyFavoriteResponse
import container.restaurant.android.databinding.FragmentFavoriteBinding
import container.restaurant.android.presentation.base.BaseFragment
import container.restaurant.android.presentation.my.adapter.MyFavoriteAdapter
import container.restaurant.android.util.hide
import container.restaurant.android.util.observe
import container.restaurant.android.util.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private val viewModel: MyViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
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

            observe(getMyFavorite(), ::completeFavorite)
        }
    }

    private fun completeFavorite(myFavoriteResponse: MyFavoriteResponse) {
        val adapter = MyFavoriteAdapter()
        binding.rvMyFeed.adapter = adapter
        adapter.submitList(myFavoriteResponse.embedded?.restaurantFavoriteDtoList)

        if(myFavoriteResponse.embedded?.restaurantFavoriteDtoList.isNullOrEmpty()) {
            binding.llEmptyInfo.show()
        } else {
            binding.llEmptyInfo.hide()
        }
    }
}