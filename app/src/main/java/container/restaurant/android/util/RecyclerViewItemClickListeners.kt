package container.restaurant.android.util

import container.restaurant.android.data.CategorySelection
import container.restaurant.android.data.response.SearchLocationResponse

interface RecyclerViewItemClickListeners {
    interface CategorySelectionItemClickListener {
        fun onCategorySelectionItemClick(item: CategorySelection, adapterPosition: Int)
    }
    interface FoodPhotoItemClickListener {
        fun onDeleteClick(adapterPosition: Int)
    }
    interface SearchResultItemClickListener {
        fun onSearchResultItemClick(item: SearchLocationResponse.Item)
    }
}