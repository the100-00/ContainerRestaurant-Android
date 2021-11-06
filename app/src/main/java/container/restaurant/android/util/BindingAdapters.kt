package container.restaurant.android.util

import android.graphics.Bitmap
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.hedgehog.ratingbar.RatingBar
import container.restaurant.android.R
import container.restaurant.android.di.BASE_URL
import de.hdodenhof.circleimageview.CircleImageView
import timber.log.Timber
import java.util.*

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

@BindingAdapter("checker")
fun bindChecker(btn: MaterialButton, chk: Boolean?) {
    if (chk != null) {
        btn.isChecked = chk
    }
}

@BindingAdapter("html_text")
fun bindHtmlText(tv: TextView, htmlText: String?) {
    if (!htmlText.isNullOrEmpty()) {
        tv.text = CommUtils.fromHtml(htmlText)
    }
}

@BindingAdapter("user_level")
fun bindUserLevel(tv: TextView, userLevel: Int?) {
    if (userLevel != null) {
        when (userLevel) {
            in (0..1) -> tv.text = String.format("Lv%d. 텀블러", userLevel)
            2 -> tv.text = String.format("Lv%d. 밥그릇", userLevel)
            3 -> tv.text = String.format("Lv%d. 금 용기", userLevel)
            4 -> tv.text = String.format("Lv%d. 후라이팬", userLevel)
            5 -> tv.text = String.format("Lv%d. 냄비", userLevel)
            else -> tv.text = String.format("Lv%d. 냄비", userLevel)
        }
    }
}

@BindingAdapter("image_from_url")
fun bindImageFromUrl(iv: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(iv.context)
            .load(url)
            .error(R.drawable.img_level_up)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(iv)
    }
}

@BindingAdapter("favorite_feed_count")
fun bindFavoriteFeedCount(tv: TextView, count: Int?) {
    if (count != null) {
        tv.text = "피드 +$count"
    }
}

@BindingAdapter("bind:imageUrlWithOutBaseUrl")
fun ImageView.setImageUrlWithoutBaseUrl(url: String?) {
    Timber.d("image Test : $url")
    url?.let {
        val baseUrl = BASE_URL.substring(0, BASE_URL.lastIndex)
        val fullUrl = "$baseUrl$url"
        Glide.with(this)
            .load(fullUrl)
            .into(this)
    }
}

@BindingAdapter("bind:imageUrlWithOutBaseUrl")
fun CircleImageView.setImageUrlWithoutBaseUrl(url: String?){
    url?.let {
        val baseUrl = BASE_URL.substring(0, BASE_URL.lastIndex)
        val fullUrl = "$baseUrl$url"
        Glide.with(this)
            .load(fullUrl)
            .into(this)
    }
}


@BindingAdapter("bind:emptyProfileRes")
fun ImageView.setEmptyProfileRes(@DrawableRes res: Int) {
    Glide.with(this)
        .load(res)
        .into(this)
}

@BindingAdapter("bind:homeIcRes")
fun ImageView.setIcRes(@DrawableRes res: Int) {
    Glide.with(this)
        .load(res)
        .into(this)
}

@BindingAdapter("bind:emptyProfileRes")
fun CircleImageView.setEmptyProfileRes(@DrawableRes res: Int) {
    Glide.with(this)
        .load(res)
        .into(this)
}


@BindingAdapter(
    value = ["bind:spannableText"
        , "bind:spannableBold"
        , "bind:spannableBoldStart"
        , "bind:spannableBoldEnd"]
    , requireAll = false
)
fun TextView.setSpannableString(
    text: String,
    isBold: String = "false",
    start: Int = 0,
    end: Int = 0
) {
    val str = SpannableStringBuilder(text)
    if (isBold.toBoolean()) {
        str.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
    setText(str)
}

@BindingAdapter("bind:imageUrlWithBaseUrl")
fun ImageView.setImageUrlWithBaseUrl(url: String?) {
    url?.let {
        Glide.with(this)
            .load(url)
            .into(this)
    }
}

@BindingAdapter("bind:imageUrlWithBaseUrl")
fun CircleImageView.setImageUrlWithBaseUrl(url: String?) {
    url?.let {
        Glide.with(this)
            .load(url)
            .into(this)
    }
}

@BindingAdapter("bind:emptyFeedBackgroundSetup")
fun ImageView.setEmptyFeedBackgroundSetup(boolean: String) {
    if (boolean.toBoolean()) {
        when (Random().nextInt(3)) {
            0 -> {
                setBackgroundResource(R.color.green_02)
            }
            1 -> {
                setBackgroundResource(R.color.blue_01)
            }
            2 -> {
                setBackgroundResource(R.color.yellow_02)
            }
        }
    }
}

@BindingAdapter("bind:htmlText")
fun TextView.setHtmlText(htmlText: String?) {
    htmlText?.let{
        text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

@BindingAdapter("bind:stripHtmlText")
fun TextView.setStrippedHtmlText(htmlText: String?) {
    htmlText?.let{
        text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }
}

@BindingAdapter("bind:srcBitmap")
fun ImageView.setBitmap(bitmap: Bitmap) {
    Glide.with(this)
        .load(bitmap)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .into(this)
}

@BindingAdapter("bind:feedCategorySelected")
fun TextView.setFeedCategorySelected(boolean: Boolean?) {
    if(boolean == true) {
        isSelected = true
        setTypeface(typeface, Typeface.BOLD)
    }
    else {
        isSelected = false
        setTypeface(Typeface.create(typeface, Typeface.NORMAL), Typeface.NORMAL)
    }
}

@BindingAdapter("bind:addTabSelectedListener")
fun TabLayout.addTabSelectedListener(listener: TabLayout.OnTabSelectedListener){
    addOnTabSelectedListener(listener)
}

@BindingAdapter("bind:registerOnPageChangeCallback")
fun ViewPager2.registerOnPageChangeCallback(onPageChangeCallback: ViewPager2.OnPageChangeCallback){
    registerOnPageChangeCallback(onPageChangeCallback)
}

@BindingAdapter("bind:setRating")
fun RatingBar.setRating(rating: Int){
    setStar(rating.toFloat())
}

@BindingAdapter("bind:clickable")
fun RatingBar.setClickable(boolean: String){
    setmClickable(boolean.toBoolean())
}

@BindingAdapter("bind:difficultyIntToText")
fun TextView.setDifficultyText(rating: Int){
    visibility = View.VISIBLE
    when(rating){
        1-> text = resources.getString(R.string.difficulty_lv1)
        2-> text = resources.getString(R.string.difficulty_lv2)
        3-> text = resources.getString(R.string.difficulty_lv3)
        4-> text = resources.getString(R.string.difficulty_lv4)
        5-> text = resources.getString(R.string.difficulty_lv5)
        else -> visibility = View.GONE
    }
}