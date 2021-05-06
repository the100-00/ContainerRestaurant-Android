package container.restaurant.android.presentation.feed.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tak8997.github.domain.ContainerFeedHistory
import container.restaurant.android.databinding.ItemContainerFeedBinding

internal class ContainerFeedAdapter : ListAdapter<ContainerFeedHistory , ContainerFeedViewHolder>(
    ContainerFeedDiffUtilCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContainerFeedViewHolder {
        val binding = ItemContainerFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContainerFeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContainerFeedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ContainerFeedViewHolder(
    private val binding: ItemContainerFeedBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(containerFeedHistory: ContainerFeedHistory) {
        binding.item = containerFeedHistory
        binding.executePendingBindings()
    }
}

class ContainerFeedDiffUtilCallback : DiffUtil.ItemCallback<ContainerFeedHistory>() {

    override fun areItemsTheSame(oldItem: ContainerFeedHistory, newItem: ContainerFeedHistory): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ContainerFeedHistory, newItem: ContainerFeedHistory): Boolean {
        return oldItem == newItem
    }
}

