package container.restaurant.android.presentation.feed.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.data.model.Feed
import container.restaurant.android.databinding.ItemContainerFeedBinding

internal class ContainerFeedAdapter : RecyclerView.Adapter<ContainerFeedViewHolder>() {

    private val items = mutableListOf<Feed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContainerFeedViewHolder {
        val binding = ItemContainerFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContainerFeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContainerFeedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Feed>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Feed>) {
        val pos = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(pos, itemCount)
    }
}

class ContainerFeedViewHolder(
    private val binding: ItemContainerFeedBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(feed: Feed) {
        binding.item = feed
        binding.executePendingBindings()
    }
}