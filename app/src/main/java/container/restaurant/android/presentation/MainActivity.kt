package container.restaurant.android.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivityMainBinding
import container.restaurant.android.presentation.base.BaseActivity
import container.restaurant.android.presentation.feed.explore.FeedExploreFragment
import container.restaurant.android.presentation.feed.write.FeedWriteActivity
import container.restaurant.android.presentation.home.HomeFragment
import container.restaurant.android.presentation.map.MapsFragment
import container.restaurant.android.util.observe
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class MainActivity : BaseActivity() {

    private val navigationController: NavigationController by inject { parametersOf(this) }

    private val viewModel: MainViewModel by viewModel()

    internal val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                lifecycleOwner = this@MainActivity
                viewModel = this@MainActivity.viewModel
            }
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            observe(navToFeed) {
                startActivity(FeedWriteActivity.getIntent(this@MainActivity))
            }
        }

        //백 스택을 이용하여 마이 페이지 로그인 취소 시 이전 탭으로 돌아가도록 설정
        supportFragmentManager.addOnBackStackChangedListener {
            //마이 페이지 상태가 아닐 때만 탭 변경하도록 함
            if(supportFragmentManager.findFragmentByTag(NavHostFragment::class.java.simpleName)==null){
                when {
                    supportFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)!=null -> {
                        binding.bottomNav.selectedItemId = BottomNavItem.HOME.menuId
                    }
                    supportFragmentManager.findFragmentByTag(FeedExploreFragment::class.java.simpleName)!=null -> {
                        binding.bottomNav.selectedItemId = BottomNavItem.FEED.menuId
                    }
                    supportFragmentManager.findFragmentByTag(MapsFragment::class.java.simpleName)!=null -> {
                        binding.bottomNav.selectedItemId = BottomNavItem.MAP.menuId
                    }
                }
            }
        }
        setupBottomNav(savedInstanceState)
    }

    private fun setupBottomNav(savedInstanceState: Bundle?) {
        binding.bottomNav.itemIconTintList = null
        binding.bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            val navItem = BottomNavItem.values().find { bottomNavItem ->
                menuItem.itemId == bottomNavItem.menuId }
            navItem?.navigate?.invoke(navigationController)
            true
        }
        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.home
        }
    }

    enum class BottomNavItem(
        @IdRes @MenuRes val menuId: Int,
        val navigate: NavigationController.() -> Unit
    ) {
        HOME(R.id.home, {
            navigateToHome()
        }),
        FEED(R.id.feed, {
            navigateToFeed()
        }),
        HIDDEN(R.id.hidden, {

        }),
        MAP(R.id.map, {
            navigateToMap()
        }),
        MY(R.id.my, {
            navigateToMy()
        })
    }
}
