package container.restaurant.android.presentation.feed.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.databinding.ItemMostFeedUserBinding

internal class MostFeedUserAdapter(
    private val onClickUser: (user: FeedUser) -> Unit
): RecyclerView.Adapter<MostFeedUserAdapter.ViewHolder>() {

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
        private val binding: ItemMostFeedUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(feedUser: FeedUser, onClickUser: (user: FeedUser) -> Unit) {
            binding.item = feedUser
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onClickUser(feedUser)
                }
            }
            binding.executePendingBindings()
        }
    }
}