package container.restaurant.android.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import container.restaurant.android.databinding.FragmentHomeBinding
import container.restaurant.android.dialog.SimpleConfirmDialog
import container.restaurant.android.presentation.auth.KakaoSignInDialogFragment
import container.restaurant.android.presentation.feed.all.FeedAllActivity
import container.restaurant.android.presentation.feed.item.ContainerFeedAdapter
import container.restaurant.android.presentation.home.item.BannerAdapter
import container.restaurant.android.presentation.user.UserProfileActivity
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HomeFragment : Fragment() {

    private val bannerAdapter by lazy {
        BannerAdapter()
    }

    private val containerFeedAdapter by lazy {
        ContainerFeedAdapter()
    }

    private val viewModel: HomeViewModel by viewModel()

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
        getBannersInfo()
        setupContainerFeedRecycler()
    }

    private fun observeData() {
        with(viewModel) {
            isNavToAllContainerFeedClicked.observe(viewLifecycleOwner) {
                startActivity(FeedAllActivity.getIntent(requireContext()))
            }
            isNavToMyContainerFeedClicked.observe(viewLifecycleOwner) {
                showMyContainerConfirmDialog()
            }
        }

        observe(viewModel.bannerList) {
            addBannerItems()
        }
    }

    private fun addBannerItems() {
        bannerAdapter.addItems(
            viewModel.bannerList.value?.bannerInfoDtoList!!
        )
    }

    private fun setUpBannerView(){
        binding.pagerIntroBanner.adapter = bannerAdapter
        TabLayoutMediator(binding.tablayoutIndicator, binding.pagerIntroBanner) { _, _ ->
        }.attach()
    }

    private fun getBannersInfo() {
        lifecycleScope.launchWhenCreated {
            viewModel.getBannersInfo()
        }
    }

    private fun showMyContainerConfirmDialog(){
        val dialog = SimpleConfirmDialog(
            titleStr = "용기낸 경험을 들려주시겠어요?",
            confirmStr = "네, 좋아요!",
            cancelStr = "나중에요"
        )
        dialog.setMultiEventListener(object : SimpleConfirmDialog.MultiEventListener {
            override fun confirmClick(dialogSelf: SimpleConfirmDialog) {
                dialogSelf.dismiss()
                if(!viewModel.isUserSignIn()){
                    KakaoSignInDialogFragment().show(childFragmentManager, "KakaoSignInDialogFragment")
                } else {
                  startActivity(UserProfileActivity.getIntent(requireContext()))
                }
            }

            override fun cancelClick(dialogSelf: SimpleConfirmDialog) {
                dialogSelf.dismiss()
            }
        })
        dialog.show(childFragmentManager,"SimpleConfirmDialog")
    }

    private fun setupContainerFeedRecycler() {
//        with(binding.rvContainerFeedHistory) {
//            layoutManager = GridLayoutManager(context ?: return, 2)
//            adapter = containerFeedAdapter
//        }
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}