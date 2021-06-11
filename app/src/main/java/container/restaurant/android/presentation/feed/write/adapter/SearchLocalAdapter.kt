package container.restaurant.android.presentation.feed.write.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.data.response.SearchLocalResponse
import container.restaurant.android.databinding.ItemSearchNameBinding

class SearchLocalAdapter(private val itemClick:(SearchLocalResponse.Item) -> Unit): ListAdapter<SearchLocalResponse.Item, RecyclerView.ViewHolder>(
    SearchLocalItemDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchLocalViewHolder(ItemSearchNameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as SearchLocalViewHolder).bind(item)
    }

    inner class SearchLocalViewHolder(private val binding: ItemSearchNameBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.place?.let { clickItem ->
                    itemClick(clickItem)
                }
            }
        }

        fun bind(item: SearchLocalResponse.Item) {
            binding.apply {
                place = item
                executePendingBindings()
            }
        }
    }
}

private class SearchLocalItemDiffCallback: DiffUtil.ItemCallback<SearchLocalResponse.Item>() {

    override fun areItemsTheSame(oldItem: SearchLocalResponse.Item, newItem: SearchLocalResponse.Item): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: SearchLocalResponse.Item, newItem: SearchLocalResponse.Item): Boolean {
        return oldItem == newItem
    }
}