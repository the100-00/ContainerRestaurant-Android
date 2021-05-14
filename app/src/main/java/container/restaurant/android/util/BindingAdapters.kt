package container.restaurant.android.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import container.restaurant.android.R

@BindingAdapter(value = ["android:imageUrl", "android:circleCrop"], requireAll = false)
fun ImageView.setImage(url: String?, circleCrop: Boolean = false) {
    if (url.isNullOrEmpty()) {
        return
    }

    Glide.with(context)
        .load(url)
        .apply {
            if (circleCrop) {
                centerCrop()
            }
        }
        .into(this)
}