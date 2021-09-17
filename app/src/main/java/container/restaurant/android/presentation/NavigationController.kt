package container.restaurant.android.presentation

import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import container.restaurant.android.R
import container.restaurant.android.presentation.feed.explore.FeedExploreFragment
import container.restaurant.android.presentation.home.HomeFragment
import container.restaurant.android.presentation.map.MapsFragment
import timber.log.Timber

class NavigationController(activity: AppCompatActivity) {

    private val containerId = R.id.container
    private val fragmentManager = activity.supportFragmentManager

    fun navigateToHome() {
        replaceFragment(HomeFragment.newInstance())
    }

    fun navigateToFeed() {
        replaceFragment(FeedExploreFragment.newInstance())
    }

    fun navigateToMap() {
        replaceFragment(MapsFragment.newInstance())
    }

    fun navigateToMy() {
        replaceFragmentNavGraph(R.navigation.my_nav)
    }

    private fun replaceFragment(fragment: Fragment) {

        val currentFragmentName =
            fragmentManager.findFragmentById(containerId)?.javaClass?.simpleName
        val fragmentName = fragment::class.java.simpleName
        Timber.d("currentFragment : $currentFragmentName")
        Timber.d("fragment : $fragmentName")
        if (currentFragmentName != fragmentName) {
            val transaction = fragmentManager
                .beginTransaction()
                .replace(containerId, fragment, fragment::class.java.simpleName)

            if (fragmentManager.isStateSaved) {
                transaction.commitAllowingStateLoss()
            } else {
                transaction.commit()
            }
        }
    }

    private fun replaceFragmentNavGraph(@NavigationRes navGraph: Int) {
        val currentFragmentName =
            fragmentManager.findFragmentById(containerId)?.javaClass?.simpleName
        val fragmentName = NavHostFragment::class.java.simpleName
        // 마이페이지 화면 상태에서 또 마이페이지 탭을 클릭하면 새로고침 안하도록 함(fragmentManager 백스택이용)
        if(currentFragmentName != fragmentName) {
            val host = NavHostFragment.create(navGraph)
            fragmentManager.beginTransaction()
                .add(containerId, host, NavHostFragment::class.java.simpleName)
                .addToBackStack(null)
                .setPrimaryNavigationFragment(host)
                .commit()
        }
    }
}