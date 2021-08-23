package container.restaurant.android.presentation.feed.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.tabs.TabLayout
import container.restaurant.android.data.FeedCategory
import container.restaurant.android.data.SortingCategory
import container.restaurant.android.util.RecyclerViewItemClickListeners

internal class FeedExploreViewModel : ViewModel(),
    RecyclerViewItemClickListeners.SortingCategoryItemClickListener {

    private val _sortingCategoryList: MutableLiveData<List<SortingCategory>> = MutableLiveData(
        SortingCategory.values().toList()
    )
    val sortingCategoryList: LiveData<List<SortingCategory>> = _sortingCategoryList

    val feedCategories = FeedCategory.values()

    val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            tab?.position?.let{
                feedCategories[it].isItemSelected.value=false
            }
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.position?.let{
                feedCategories[it].isItemSelected.value=true
            }
        }
    }

    override fun onSortingCategoryItemClick(adapterPosition: Int) {
        if(sortingCategoryList.value!=null){
            for((index, category) in _sortingCategoryList.value!!.withIndex()) {
                category.isItemSelected.value = index == adapterPosition
            }
        }
    }
}