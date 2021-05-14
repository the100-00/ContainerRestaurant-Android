package container.restaurant.android.presentation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import container.restaurant.android.R
import container.restaurant.android.databinding.ActivityMainBinding
import container.restaurant.android.presentation.feed.write.FeedWriteActivity
import container.restaurant.android.util.observe
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity : AppCompatActivity() {

    private val navigationController: NavigationController by inject { parametersOf(this) }

    private val viewModel: MainViewModel by viewModel()

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                lifecycleOwner = this@MainActivity
                viewModel = this@MainActivity.viewModel
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            navToFeed.observe(this@MainActivity) {
                startActivity(FeedWriteActivity.getIntent(this@MainActivity))
            }
        }

        setupBottomNav(savedInstanceState)
    }

    private fun setupBottomNav(savedInstanceState: Bundle?) {
        binding.bottomNav.itemIconTintList = null
        binding.bottomNav.setOnNavigationItemSelectedListener {
            val navItem = BottomNavItem.forId(it.itemId)
            navItem.navigate(navigationController)

            true
        }
        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.home
        }
    }

    enum class BottomNavItem(
        @MenuRes val menuId: Int,
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
        ;

        companion object {
            fun forId(@IdRes id: Int): BottomNavItem {
                return values().first { it.menuId == id }
            }
        }
    }
}
