package container.restaurant.android.presentation

import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
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
        replaceFragmentNavGraph(R.navigation.my_nav)
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

    private fun replaceFragmentNavGraph(@NavigationRes navGraph: Int){
        val host = NavHostFragment.create(navGraph)
        fragmentManager.beginTransaction()
            .replace(containerId, host)
            .setPrimaryNavigationFragment(host)
            .commit()
    }
}