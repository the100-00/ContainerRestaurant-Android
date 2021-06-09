package container.restaurant.android.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import container.restaurant.android.R
import container.restaurant.android.presentation.feed.FeedFragment
import container.restaurant.android.presentation.home.HomeFragment
import container.restaurant.android.presentation.map.MapsFragment
import container.restaurant.android.presentation.my.MyFragment

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
        replaceFragment(MapsFragment.newInstance())
    }

    fun navigateToMy() {
        replaceFragment(MyFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = fragmentManager
            .beginTransaction()
            .replace(containerId, fragment, "")

        if (fragmentManager.isStateSaved) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }
}