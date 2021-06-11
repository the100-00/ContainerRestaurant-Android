package container.restaurant.android.presentation.my.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.data.response.MyFavoriteResponse
import container.restaurant.android.databinding.ListItemFavoriteBinding

class MyFavoriteAdapter : ListAdapter<MyFavoriteResponse.Embedded.RestaurantFavoriteDto, RecyclerView.ViewHolder>(MyFavoriteItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyFavoriteViewHolder(ListItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as MyFavoriteViewHolder).bind(item)
    }

    class MyFavoriteViewHolder(private val binding: ListItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyFavoriteResponse.Embedded.RestaurantFavoriteDto) {
            binding.apply {
                favorite = item
                executePendingBindings()
            }
        }
    }
}

private class MyFavoriteItemDiffCallback: DiffUtil.ItemCallback<MyFavoriteResponse.Embedded.RestaurantFavoriteDto>() {
    override fun areItemsTheSame(
        oldItem: MyFavoriteResponse.Embedded.RestaurantFavoriteDto,
        newItem: MyFavoriteResponse.Embedded.RestaurantFavoriteDto
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MyFavoriteResponse.Embedded.RestaurantFavoriteDto,
        newItem: MyFavoriteResponse.Embedded.RestaurantFavoriteDto
    ): Boolean {
        return oldItem == newItem
    }

}