package container.restaurant.android.presentation.feed.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivityFeedDetailBinding
import container.restaurant.android.presentation.base.BaseActivity
import container.restaurant.android.util.CommUtils
import container.restaurant.android.util.DataTransfer
import container.restaurant.android.util.EventObserver
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityFeedDetailBinding

    private val viewModel: FeedDetailViewModel by viewModel()

    private var feedAdapter: FragmentStateAdapter? = null
    private val tabText: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityFeedDetailBinding>(
            this,
            R.layout.activity_feed_detail
        )
            .apply {
                viewModel = this@FeedDetailActivity.viewModel
                lifecycleOwner = this@FeedDetailActivity
            }

        val feedId = intent.getIntExtra(DataTransfer.FEED_ID, -1)
        lifecycleScope.launchWhenCreated {
            viewModel.getFeedDetail(feedId)
        }

        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observe(ownerContainerLevel) { ownerLevelTitle ->
                if (ownerProfileUrl.value == null) {
                    ownerProfileRes.value =
                        CommUtils.getEmptyProfileResId(applicationContext, ownerLevelTitle)
                }
            }
            observe(content) {
                feedAdapter = getFragmentStateAdapter(it)
                setupFeedPager()
            }
            isBackButtonClicked.observe(this@FeedDetailActivity, EventObserver{
                if(it){
                    onBackPressed()
                }
            })
        }
    }

    // 용기낸 소감(내용)이 있는지 없는 지에 따라 다른 페이저 어댑터 생성
    private fun getFragmentStateAdapter(content: String): FragmentStateAdapter {
        if (content.isNotEmpty()) {
            tabText.add("용기낸 소감")
            tabText.add("용기낸 정보")
            return object : FragmentStateAdapter(this@FeedDetailActivity) {
                override fun getItemCount(): Int = 2

                override fun createFragment(position: Int): Fragment = when (position) {
                    0 -> {
                        FeedDetailContentFragment()
                    }
                    else -> {
                        FeedDetailInfoFragment()
                    }
                }

            }
        } else {
            tabText.add("용기낸 정보")
            return object : FragmentStateAdapter(this@FeedDetailActivity) {
                override fun getItemCount(): Int = 1

                override fun createFragment(position: Int): Fragment {
                    return FeedDetailInfoFragment()
                }
            }
        }
    }

    private fun setupFeedPager() {
        binding.viewPager.adapter = feedAdapter

//        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                val view = binding.viewPager
//                view.post {
//                    val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
//                    val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//                    view.measure(wMeasureSpec, hMeasureSpec)
//
//                    if (view.layoutParams.height != view.measuredHeight) {
//                        // ParentViewGroup is, for example, LinearLayout
//                        // ... or whatever the parent of the ViewPager2 is
//                        view.layoutParams = (view.layoutParams as ConstraintLayout.LayoutParams)
//                            .also { lp -> lp.height = view.measuredHeight }
//                    }
//                }
//            }
//        })

        // tabLayout 과 viewPager 를 연결
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = tabText[pos]
        }.attach()

    }

    companion object {
        fun getIntent(activity: Activity) = Intent(activity, FeedDetailActivity::class.java)
    }
}