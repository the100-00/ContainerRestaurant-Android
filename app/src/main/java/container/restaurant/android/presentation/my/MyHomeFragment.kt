package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentMyHomeBinding
import container.restaurant.android.presentation.auth.KakaoSignInDialogFragment
import container.restaurant.android.presentation.base.BaseFragment
import container.restaurant.android.util.EventObserver
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyHomeFragment : BaseFragment() {

    private lateinit var binding: FragmentMyHomeBinding

    private val viewModel: MyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyHomeBinding.inflate(inflater, container, false)
            .apply {
                this.viewModel = this@MyHomeFragment.viewModel
                this.lifecycleOwner = this@MyHomeFragment
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        logInCheck()
    }

    private fun logInCheck() {
        if (!viewModel.isUserSignIn()) {
            KakaoSignInDialogFragment().show(childFragmentManager, "KakaoSignInDialogFragment")
        } else {
            lifecycleScope.launchWhenCreated {
                viewModel.getUserInfo()
            }
        }
    }

    private fun observeData() {
        with(viewModel) {
            isSettingButtonClicked.observe(viewLifecycleOwner, EventObserver {
                if (it) {
                    navigateToSetting()
                }
            })
            isMyFeedButtonClicked.observe(viewLifecycleOwner, EventObserver {
                if (it) {
                    navigateToMyFeed()
                }
            })
            isScrapFeedButtonClicked.observe(viewLifecycleOwner, EventObserver {
                if (it) {
                    navigateToScrap()
                }
            })
            isBookmarkedRestaurantButtonClicked.observe(viewLifecycleOwner, EventObserver {
                if (it) {
                    navigateToBookmarkedRestaurant()
                }
            })
            isNicknameChangeButtonClicked.observe(viewLifecycleOwner, EventObserver {
                if (it) {
                    navigateToChangeName()
                }
            })
            observe(userLevelTitle) { userLevelTitle ->
                if (userProfileUrl.value == null) {
                    when (userLevelTitle) {
                        getString(R.string.empty_profile_lv1) -> userProfileRes.value =
                            R.drawable.empty_profile_lv1
                        getString(R.string.empty_profile_lv2) -> userProfileRes.value =
                            R.drawable.empty_profile_lv2
                        getString(R.string.empty_profile_lv3) -> userProfileRes.value =
                            R.drawable.empty_profile_lv3
                        getString(R.string.empty_profile_lv4) -> userProfileRes.value =
                            R.drawable.empty_profile_lv4
                        getString(R.string.empty_profile_lv5) -> userProfileRes.value =
                            R.drawable.empty_profile_lv5
                    }
                }
            }
        }
    }

    private fun navigateToChangeName() {
        with(viewModel) {
            if (userId.value != null && userNickName.value != null) {
                val directions = MyHomeFragmentDirections.actionMyHomeFragmentToChangeNameFragment(
                    userId.value!!,
                    viewModel.userNickName.value!!
                )
                findNavController().navigate(directions)
            }
            else {
                Toast.makeText(requireContext(), "유효하지 않은 아이디와 닉네임입니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun navigateToMyFeed() {
        viewModel.userId.value?.let {
            val directions = MyHomeFragmentDirections.actionMyHomeFragmentToMyFeedFragment(it)
            findNavController().navigate(directions)
        }
    }

    private fun navigateToScrap() {
        viewModel.userId.value?.let {
            val directions = MyHomeFragmentDirections.actionMyHomeFragmentToMyScrapFragment(it)
            findNavController().navigate(directions)
        }
    }

    private fun navigateToBookmarkedRestaurant() {
        val directions = MyHomeFragmentDirections.actionMyHomeFragmentToFavoriteFragment()
        findNavController().navigate(directions)
    }

    private fun navigateToSetting() {
        val directions = MyHomeFragmentDirections.actionMyHomeFragmentToMySettingFragment()
        findNavController().navigate(directions)
    }


}