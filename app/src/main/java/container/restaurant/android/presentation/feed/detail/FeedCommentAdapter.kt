package container.restaurant.android.presentation.feed.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.data.model.FeedComment
import container.restaurant.android.databinding.ItemFeedCommentBinding

internal class FeedCommentAdapter : RecyclerView.Adapter<FeedCommentAdapter.ViewHolder>() {

    private val items = mutableListOf<FeedComment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFeedCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<FeedComment>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(feedComment: FeedComment) {
        this.items.add(feedComment)
        notifyItemInserted(itemCount)
    }

    class ViewHolder(private val binding: ItemFeedCommentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(feedComment: FeedComment) {
            binding.item = feedComment
            binding.executePendingBindings()
        }
    }
}