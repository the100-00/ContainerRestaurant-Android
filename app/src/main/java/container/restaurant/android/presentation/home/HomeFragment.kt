package container.restaurant.android.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.tak8997.github.domain.BannerContent
import container.restaurant.android.databinding.FragmentHomeBinding
import container.restaurant.android.dialog.SimpleConfirmDialog
import container.restaurant.android.presentation.auth.KakaoSignInDialogFragment
import container.restaurant.android.presentation.feed.all.FeedAllActivity
import container.restaurant.android.presentation.feed.item.ContainerFeedAdapter
import container.restaurant.android.presentation.home.item.BannerAdapter
import container.restaurant.android.presentation.user.UserProfileActivity
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
        with(viewModel) {
            isNavToAllContainerFeedClicked.observe(viewLifecycleOwner) {
                startActivity(FeedAllActivity.getIntent(requireContext()))
            }
            isNavToMyContainerFeedClicked.observe(viewLifecycleOwner) {
                showMyContainerConfirmDialog()
            }
        }

        setupBannerPager()
        setupContainerFeedRecycler()
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
        with(binding.rvContainerFeedHistory) {
            layoutManager = GridLayoutManager(context ?: return, 2)
            adapter = containerFeedAdapter
        }
    }

    private fun setupBannerPager() {
        binding.pagerIntroBanner.adapter = bannerAdapter
        TabLayoutMediator(binding.tablayoutIndicator, binding.pagerIntroBanner) { _, _ ->
        }.attach()
        // test dummy
        bannerAdapter.addItems(
            listOf(
                BannerContent("this is title", "this is content"),
                BannerContent("this is title", "this is content"),
                BannerContent("this is title", "this is content")
            )
        )
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}