package container.restaurant.android.presentation.map.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.R
import container.restaurant.android.data.model.RestaurantNearInfoDto
import container.restaurant.android.databinding.ItemResBinding

internal class MapResAdapter : RecyclerView.Adapter<MapResAdapter.MapResViewHolder>() {

    private val items = mutableListOf<RestaurantNearInfoDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapResViewHolder {
        val binding : ItemResBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_res,parent,false)
        return MapResViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapResViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: RestaurantNearInfoDto) {
        this.items.clear()
        this.items.add(items)
        notifyDataSetChanged()
    }


    class MapResViewHolder(private val binding: ItemResBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(conRes: RestaurantNearInfoDto) {
            binding.item = conRes
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}