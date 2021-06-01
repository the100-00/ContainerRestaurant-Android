package container.restaurant.android.presentation.feed.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.data.model.Menu
import container.restaurant.android.databinding.ItemMenuBinding

internal class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private val items = mutableListOf<Menu>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(
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

    fun setItems(items: List<Menu>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: Menu) {
            binding.item = menu
            binding.executePendingBindings()
        }
    }
}