package container.restaurant.android.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import container.restaurant.android.data.SortingCategory
import container.restaurant.android.util.RecyclerViewItemClickListeners

internal class FeedExploreViewModel : ViewModel(),
    RecyclerViewItemClickListeners.SortingCategoryItemClickListener {

    private val _sortingCategoryList: MutableLiveData<List<SortingCategory>> = MutableLiveData(
        SortingCategory.values().toList()
    )
    val sortingCategoryList: LiveData<List<SortingCategory>> = _sortingCategoryList

    override fun onSortingCategoryItemClick(adapterPosition: Int) {
        if(sortingCategoryList.value!=null){
            for((index, category) in _sortingCategoryList.value!!.withIndex()) {
                category.selected.value = index == adapterPosition
            }
        }
    }
}