package container.restaurant.android.presentation.feed.write.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.data.db.MainFood
import container.restaurant.android.databinding.ItemMainMenuBinding

class MainFoodAdapter : ListAdapter<MainFood, RecyclerView.ViewHolder>(MainFoodItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainFoodViewHolder(ItemMainMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as MainFoodViewHolder).bind(item)
    }

    inner class MainFoodViewHolder(private val binding:ItemMainMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.etMenuName.addTextChangedListener { text ->
                binding.food?.let {  mainFood ->
                    subscribeFoodName(mainFood, text.toString())
                }
            }
            binding.etContainer.addTextChangedListener { text ->
                binding.food?.let { mainFood ->
                    subscribeContainer(mainFood, text.toString())
                }
            }
        }

        fun bind(item: MainFood) {
            binding.apply {
                food = item
                executePendingBindings()
            }
        }
    }

    private fun subscribeFoodName(mainFood: MainFood, text: String) {
        currentList.forEach {
            if(it.id == mainFood.id) {
                it.foodName = text
            }
        }
    }

    private fun subscribeContainer(mainFood: MainFood, text: String) {
        currentList.forEach {
            if(it.id == mainFood.id) {
                it.bottle = text
            }
        }
    }
}

private class MainFoodItemDiffCallback: DiffUtil.ItemCallback<MainFood>() {
    override fun areItemsTheSame(oldItem: MainFood, newItem: MainFood): Boolean {
        return oldItem.foodName == newItem.foodName
    }

    override fun areContentsTheSame(oldItem: MainFood, newItem: MainFood): Boolean {
        return oldItem == newItem
    }
}