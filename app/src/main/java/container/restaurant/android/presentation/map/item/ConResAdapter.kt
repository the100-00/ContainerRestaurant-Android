package container.restaurant.android.presentation.map.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.R
import container.restaurant.android.data.model.Feed
import container.restaurant.android.databinding.ItemContainerFeedBinding

internal class ConResAdapter : RecyclerView.Adapter<ConResAdapter.ConResViewHolder>() {

    private val items = mutableListOf<Feed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConResViewHolder {
        val binding : ItemContainerFeedBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_container_feed,parent,false)
        return ConResViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConResViewHolder, position: Int) {
        holder.bind(items[position])
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

    class ConResViewHolder(private val binding: ItemContainerFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(feed: Feed) {
            binding.item = feed
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}