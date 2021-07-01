package container.restaurant.android.presentation.map.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.R
import container.restaurant.android.data.response.RestaurantNearInfoDto
import container.restaurant.android.databinding.ItemResListBinding

internal class NearResAdapter : RecyclerView.Adapter<NearResAdapter.NearResViewHolder>() {

    private val items = mutableListOf<RestaurantNearInfoDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearResViewHolder {
        val binding : ItemResListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_res_list,parent,false)
        return NearResViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NearResViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<RestaurantNearInfoDto>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class NearResViewHolder(private val binding: ItemResListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(nearRes: RestaurantNearInfoDto) {
            binding.item = nearRes
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}