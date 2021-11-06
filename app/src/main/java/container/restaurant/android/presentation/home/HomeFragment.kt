package container.restaurant.android.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import container.restaurant.android.data.response.UserInfoResponse
import container.restaurant.android.databinding.FragmentHomeBinding
import container.restaurant.android.dialog.SimpleConfirmDialog
import container.restaurant.android.presentation.auth.AuthViewModel
import container.restaurant.android.presentation.auth.KakaoSignInDialogFragment
import container.restaurant.android.presentation.feed.all.FeedAllActivity
import container.restaurant.android.presentation.home.item.BannerAdapter
import container.restaurant.android.presentation.user.UserProfileActivity
import container.restaurant.android.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

internal class HomeFragment : Fragment() {

    private val bannerAdapter by lazy {
        BannerAdapter()
    }

    private val viewModel: HomeViewModel by viewModel()
    private val authViewModel: AuthViewModel by viewModel()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
            .apply {
                lifecycleOwner = this@HomeFragment
                viewModel = this@HomeFragment.viewModel
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setUpBannerView()
        getHomeInfo()
        getRecommendedFeedList()
    }

    private fun observeData() {
        with(viewModel) {
            isNavToAllContainerFeedClicked.observe(viewLifecycleOwner) {
                startActivity(FeedAllActivity.getIntent(requireContext()))
            }
            isNavToMyContainerFeedClicked.observe(viewLifecycleOwner) {
                showMyContainerConfirmDialog()
            }
            userLevelTitle.observe(viewLifecycleOwner) { userLevelTitle ->
                if (userProfileUrl.value == null) {
                    setUserProfileResByLevelTitle(requireContext(), userProfileRes, userLevelTitle)
                }
                setHomeIconByLevelTitle(requireContext(), homeIconResByUserLevel, userLevelTitle)
            }
        }

        observe(viewModel.bannerList) {
            addBannerItems()
        }

        observe(viewModel.recommendedFeedList){

        }


    }

    private fun addBannerItems() {
        viewModel.bannerList.value?.let{
            bannerAdapter.addItems(it)
        }

    }

    private fun setUpBannerView(){
        binding.pagerIntroBanner.adapter = bannerAdapter
        TabLayoutMediator(binding.tablayoutIndicator, binding.pagerIntroBanner) { _, _ ->
        }.attach()
    }

    private fun getHomeInfo() {
        lifecycleScope.launchWhenCreated {
            viewModel.getHomeInfo()
        }
    }

    private fun getRecommendedFeedList() {
        lifecycleScope.launchWhenCreated {
            viewModel.getRecommendedFeedList()
        }
    }

    private fun showMyContainerConfirmDialog(){
        val dialog = SimpleConfirmDialog(
            titleStr = "용기낸 경험을 들려주시겠어요?",
            rightBtnStr = "네, 좋아요!",
            leftBtnStr = "나중에요"
        )
        dialog.setMultiEventListener(object : SimpleConfirmDialog.MultiEventListener {
            override fun onRightBtnClick(dialogSelf: SimpleConfirmDialog) {
                dialogSelf.dismiss()
                if(!viewModel.isUserSignIn()){
                    KakaoSignInDialogFragment().show(childFragmentManager, "KakaoSignInDialogFragment")
                } else {
                  startActivity(UserProfileActivity.getIntent(requireContext()))
                }
            }

            override fun onLeftBtnClick(dialogSelf: SimpleConfirmDialog) {
                dialogSelf.dismiss()
            }
        })
        dialog.show(childFragmentManager,"SimpleConfirmDialog")
    }

    private fun logInCheck() {
        // 로그인 성공 했을 때 동작
        val onSignInSuccess: (UserInfoResponse) -> Unit = {
            Timber.d("signInSuccess At MyHomeFragment")
            with(viewModel) {
                userNickName.value = it.nickname
                userFeedCount.value = it.feedCount
                userProfileUrl.value = it.profile
                userLevelTitle.value = it.levelTitle
            }
        }

        // 가입 완료후 업데이트 할 정보
        fun updateData() {
            with(viewModel) {

            }
        }
        val signUpResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            updateData()
        }

        // 프로젝트에 저장된 토큰 없을 때
        if (!authViewModel.isUserSignIn()) {
            val kakaoSignInDialogFragment = KakaoSignInDialogFragment()
            kakaoSignInDialogFragment.setOnCloseListener(object : OnCloseListener {
                override fun onClose() {
                    parentFragment?.parentFragmentManager?.popBackStack()
                }
            })
            observeKakaoFragmentData(
                requireActivity(),
                kakaoSignInDialogFragment,
                signUpResultLauncher
            )
            kakaoSignInDialogFragment.show(childFragmentManager, "KakaoSignInDialogFragment")

            observeAuthViewModelUserInfo(viewLifecycleOwner, kakaoSignInDialogFragment.viewModel, onSignInSuccess)
        }

        // 프로젝트에 저장된 토큰 있을 때
        else {
            lifecycleScope.launchWhenCreated {
                ifAlreadySignIn(authViewModel, requireActivity())
            }
        }

        observeAuthViewModelUserInfo(viewLifecycleOwner, authViewModel, onSignInSuccess)
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}