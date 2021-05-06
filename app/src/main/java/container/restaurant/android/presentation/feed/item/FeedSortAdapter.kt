package container.restaurant.android.presentation.feed.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.databinding.ItemSortBinding

internal class FeedSortAdapter(
    private val onClickSort: (feedSort: FeedSort) -> Unit
) : RecyclerView.Adapter<FeedSortAdapter.ViewHolder>() {

    private val items = FeedSort.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSortBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            items.forEach {
                it.selected = false
            }
            items[position].selected = true
            onClickSort(items[position])
            notifyDataSetChanged()
        }
    }

    class ViewHolder(
        val binding: ItemSortBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sort: FeedSort) {
            binding.item = sort.sort
            binding.lytSortBg.isSelected = sort.selected
            binding.executePendingBindings()
        }
    }
}

enum class FeedSort(val sort: String, var selected: Boolean) {
    LATEST("최신순", true),
    LIKE("좋아요 많은 ", false),
    EASY("난이도 낮은 순", false)
    ;
}