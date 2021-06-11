package container.restaurant.android.presentation.my

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.BuildConfig
import container.restaurant.android.R
import container.restaurant.android.databinding.ItemSettingsAlarmBinding
import container.restaurant.android.util.toPx

internal class SettingsAdapter(private val items: List<Settings>) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val binding = ItemSettingsAlarmBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SettingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SettingsViewHolder(private val binding: ItemSettingsAlarmBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(settings: Settings) {
            binding.item = settings
            val container = binding.container
            when (settings.settingViewType) {
                SettingsFragment.TYPE_ALARM -> {
                    val alarmSwitch = SwitchCompat(binding.root.context)
                        .apply {
                            minWidth = 0
                            minHeight = 0
                            updatePadding(right = 0)
                            thumbTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.white))
                            trackDrawable = ContextCompat.getDrawable(binding.root.context, R.drawable.selector_alarm_switch_track)
                        }
                    container.addView(
                        alarmSwitch, FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                        )
                    )
                }
                SettingsFragment.TYPE_NAV -> {
                    val image = ImageView(binding.root.context)
                    image.setBackgroundResource(R.drawable.ic_chevron_right_outline)
                    container.addView(
                        image, FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            updateMargins(right = 6.toPx())
                        }
                    )
                }
                SettingsFragment.TYPE_TEXT -> {
                    val text = TextView(binding.root.context)
                    text.text = StringBuilder().append("v" + BuildConfig.VERSION_NAME).toString()
                    container.addView(
                        text, FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            updateMargins(right = 6.toPx())
                        }
                    )
                }
            }
            binding.executePendingBindings()
        }
    }
}
