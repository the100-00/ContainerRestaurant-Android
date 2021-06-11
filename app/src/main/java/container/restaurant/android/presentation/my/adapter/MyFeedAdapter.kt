package container.restaurant.android.presentation.my.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.data.response.MyFeedResponse
import container.restaurant.android.databinding.ListItemMyFeedBinding

class MyFeedAdapter : ListAdapter<MyFeedResponse.Embedded.FeedPreviewDto, RecyclerView.ViewHolder>(MyFeedItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyFeedViewHolder(ListItemMyFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as MyFeedViewHolder).bind(item)
    }

    class MyFeedViewHolder(private val binding: ListItemMyFeedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyFeedResponse.Embedded.FeedPreviewDto) {
            binding.apply {
                feedData = item
                executePendingBindings()
            }
        }
    }
}

private class MyFeedItemDiffCallback: DiffUtil.ItemCallback<MyFeedResponse.Embedded.FeedPreviewDto>() {
    override fun areItemsTheSame(oldItem: MyFeedResponse.Embedded.FeedPreviewDto, newItem: MyFeedResponse.Embedded.FeedPreviewDto ): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: MyFeedResponse.Embedded.FeedPreviewDto, newItem: MyFeedResponse.Embedded.FeedPreviewDto ): Boolean {
        return oldItem == newItem
    }
}