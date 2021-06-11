package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import container.restaurant.android.data.response.UserResponse
import container.restaurant.android.databinding.FragmentMyHomeBinding
import container.restaurant.android.presentation.base.BaseFragment
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyHomeFragment : BaseFragment(), View.OnClickListener  {

    private lateinit var binding: FragmentMyHomeBinding

    private val viewModel: MyViewModel by viewModel()

    private var userId: Int = 1
    private lateinit var nickName: String
    private lateinit var profile: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        setBindItem()
        subscribeUi()

        return binding.root
    }

    private fun setBindItem() {
        binding.apply {
            imgMyFeed.setOnClickListener(this@MyHomeFragment)
            imgScrapFeed.setOnClickListener(this@MyHomeFragment)
            imgFavorite.setOnClickListener(this@MyHomeFragment)
            tvUserNickname.setOnClickListener(this@MyHomeFragment)
            imgSettings.setOnClickListener(this@MyHomeFragment)
        }
    }

    private fun subscribeUi() {
        with(viewModel) {
            observe(viewLoading, ::loadingCheck)
            observe(getErrorMsg, ::errorDialog)

            observe(tempLogin()) {}
            observe(loginChk.asLiveData(), ::completedLogin)
        }
    }

    private fun completedLogin(chk: Boolean) {
        if(chk) {
            viewModel.loginChk.value = false

            observe(viewModel.getUser(), ::getUserData)
        }
    }

    private fun getUserData(userResponse: UserResponse) {
        binding.user = userResponse
        userId = userResponse.id
        nickName = userResponse.nickname
        profile = userResponse.profile
    }

    private fun navigateToChangeName() {
        val directions = MyHomeFragmentDirections.actionMyHomeFragmentToChangeNameFragment(userId, nickName, profile)
        findNavController().navigate(directions)
    }

    private fun navigateToMyFeed() {
        val directions = MyHomeFragmentDirections.actionMyHomeFragmentToMyFeedFragment(userId)
        findNavController().navigate(directions)
    }

    private fun navigateToScrap() {
        val directions = MyHomeFragmentDirections.actionMyHomeFragmentToMyScrapFragment(userId)
        findNavController().navigate(directions)
    }

    private fun navigateToFavorite() {
        val directions = MyHomeFragmentDirections.actionMyHomeFragmentToFavoriteFragment()
        findNavController().navigate(directions)
    }

    private fun navigateToSetting() {
        val directions = MyHomeFragmentDirections.actionMyHomeFragmentToMySettingFragment()
        findNavController().navigate(directions)
    }

    override fun onClick(view: View?) {
        when(view) {
            binding.imgMyFeed -> navigateToMyFeed()
            binding.imgScrapFeed -> navigateToScrap()
            binding.imgFavorite -> navigateToFavorite()
            binding.tvUserNickname -> navigateToChangeName()
            binding.imgSettings -> navigateToSetting()
        }
    }

}