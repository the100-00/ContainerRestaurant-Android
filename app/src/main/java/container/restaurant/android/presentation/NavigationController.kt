package container.restaurant.android.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import container.restaurant.android.R
import container.restaurant.android.presentation.feed.FeedFragment
import container.restaurant.android.presentation.home.HomeFragment
import container.restaurant.android.presentation.map.MapFragment
import container.restaurant.android.presentation.my.MyFragment
import container.restaurant.android.presentation.my.SettingsFragment

class NavigationController(private val activity: AppCompatActivity) {

    private val containerId = R.id.container
    private val fragmentManager = activity.supportFragmentManager

    fun navigateToHome() {
        replaceFragment(HomeFragment.newInstance())
    }

    fun navigateToFeed() {
        replaceFragment(FeedFragment.newInstance())
    }

    fun navigateToMap() {
        replaceFragment(MapFragment.newInstance())
    }

    fun navigateToMy() {
        replaceFragment(MyFragment.newInstance())
    }

    fun navigateToSettings() {
        replaceFragment(SettingsFragment.newInstance(), true)
    }

    private fun replaceFragment(fragment: Fragment, backStack: Boolean = false) {
        val transaction = fragmentManager
            .beginTransaction().apply {
                if (backStack) {
                    addToBackStack(null)
                }
            }
            .replace(containerId, fragment, "")

        if (fragmentManager.isStateSaved) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }
}