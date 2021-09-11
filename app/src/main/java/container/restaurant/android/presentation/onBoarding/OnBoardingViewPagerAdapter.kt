package container.restaurant.android.presentation.onBoarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import container.restaurant.android.R

class OnBoardingViewPagerAdapter(private val pageList: List<PageData>):
    RecyclerView.Adapter<OnBoardingViewPagerAdapter.ViewHolderPage>() {
    class ViewHolderPage(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        private val tvSubTitle = itemView.findViewById<TextView>(R.id.tv_sub_title)
        private val lottieAnimationView = itemView.findViewById<LottieAnimationView>(R.id.lottie_animation_view)

        lateinit var data: PageData

        fun onBind(data: PageData) {
            this.data = data

            tvTitle.text = data.title
            tvSubTitle.text = data.subTitle
            lottieAnimationView.setAnimation(data.lottieFileName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPage {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_on_boarding, parent, false)
        return ViewHolderPage(view)
    }

    override fun onBindViewHolder(holder: ViewHolderPage, position: Int) {
        holder.onBind(pageList[position])
    }

    override fun getItemCount(): Int {
        return pageList.size
    }
}