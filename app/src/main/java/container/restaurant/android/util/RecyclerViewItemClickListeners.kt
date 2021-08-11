package container.restaurant.android.util

import container.restaurant.android.data.CategorySelection

interface RecyclerViewItemClickListeners {
    interface CategorySelectionItemClickListener {
        fun onClick(item: CategorySelection, adapterPosition: Int)
    }
}