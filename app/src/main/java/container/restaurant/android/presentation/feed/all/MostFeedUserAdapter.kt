package container.restaurant.android.presentation.feed.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.databinding.ItemMostFeedUserBinding

internal class MostFeedUserAdapter: RecyclerView.Adapter<MostFeedUserAdapter.ViewHolder>() {

    private val items = mutableListOf<FeedUser>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMostFeedUserBinding.inflate(
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

    fun setItems(items: List<FeedUser>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemMostFeedUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(feedUser: FeedUser) {
            binding.item = feedUser
            binding.executePendingBindings()
        }
    }
}