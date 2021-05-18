package container.restaurant.android.presentation.feed.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.databinding.ItemFeedUserBinding

internal class FeedUserAdapter(
    private val onClickUser: (user: FeedUser) -> Unit
) : RecyclerView.Adapter<FeedUserAdapter.ViewHolder>() {

    private val items = mutableListOf<FeedUser>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFeedUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onClickUser)
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
        private val binding: ItemFeedUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(feedUser: FeedUser, onClickUser: (user: FeedUser) -> Unit) {
            itemView.setOnClickListener {
                onClickUser(feedUser)
            }
            binding.item = feedUser
            binding.executePendingBindings()
        }
    }
}