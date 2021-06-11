package container.restaurant.android.presentation.feed.write.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.data.db.SideDish
import container.restaurant.android.databinding.ItemSideMenuBinding

class SideDishAdapter : ListAdapter<SideDish, RecyclerView.ViewHolder>(SideDishItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SideDishViewHolder(ItemSideMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as SideDishViewHolder).bind(item)
    }

    inner class SideDishViewHolder(private val binding: ItemSideMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.etQuantity.addTextChangedListener { text ->
                binding.side?.let { sideDish ->
                    subscribeQuantity(sideDish, text.toString())
                }
            }

            binding.etBottle.addTextChangedListener { text ->
                binding.side?.let { sideDish ->
                    subscribeBottle(sideDish, text.toString())
                }
            }
        }

        fun bind(item: SideDish) {
            binding.apply {
                side = item
                executePendingBindings()
            }
        }
    }

    private fun subscribeQuantity(side: SideDish, text:String) {
        currentList.forEach {
            if(it.id == side.id)
                it.quantity = text
        }
    }

    private fun subscribeBottle(side: SideDish, text:String) {
        currentList.forEach {
            if(it.id == side.id)
                it.bottle = text
        }
    }
}

private class SideDishItemDiffCallback: DiffUtil.ItemCallback<SideDish>() {
    override fun areItemsTheSame(oldItem: SideDish, newItem: SideDish): Boolean {
        return oldItem.quantity == newItem.quantity
    }

    override fun areContentsTheSame(oldItem: SideDish, newItem: SideDish): Boolean {
        return oldItem == newItem
    }
}