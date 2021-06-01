package container.restaurant.android.presentation.feed.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.R
import container.restaurant.android.databinding.ItemLevelBinding

internal class LevelAdapter : RecyclerView.Adapter<LevelAdapter.ViewHolder>() {

    private val items = mutableListOf(5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLevelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }

        payloads.forEach {
            if (it is String && it == PAYLOAD) {
                holder.setLevel()
                Log.d("MY_LOG", "payload")
                return
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(level: Int) {
        notifyItemRangeChanged(0, level, PAYLOAD)
    }

    class ViewHolder(
        private val binding: ItemLevelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setLevel() {
            binding.level.setImageResource(R.drawable.hard_filled)
            binding.executePendingBindings()
        }

        fun bind(pos: Int) {}
    }

    companion object {
        private const val PAYLOAD = "level"
    }
}