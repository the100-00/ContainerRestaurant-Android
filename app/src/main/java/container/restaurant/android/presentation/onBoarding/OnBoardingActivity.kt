package container.restaurant.android.presentation.onBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import container.restaurant.android.R
import container.restaurant.android.data.SharedPrefStorage
import container.restaurant.android.presentation.MainActivity

//복잡한 구조를 선택할 필요가 없어 MVC로 진행함
class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val dotsIndicator = findViewById<DotsIndicator>(R.id.dots_indicator)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)
        val btnSkip = findViewById<TextView>(R.id.btn_skip)
        val btnNext = findViewById<Button>(R.id.btn_next)

        val pageList: MutableList<PageData> = mutableListOf(
            PageData(
                getString(R.string.on_boarding_title1),
                getString(R.string.on_boarding_sub_title1),
                getString(R.string.on_boarding_file_name1)
            ),
            PageData(
                getString(R.string.on_boarding_title2),
                getString(R.string.on_boarding_sub_title2),
                getString(R.string.on_boarding_file_name2)
            ),
            PageData(
                getString(R.string.on_boarding_title3),
                getString(R.string.on_boarding_sub_title3),
                getString(R.string.on_boarding_file_name3)
            ),
            PageData(
                getString(R.string.on_boarding_title4),
                getString(R.string.on_boarding_sub_title4),
                getString(R.string.on_boarding_file_name4)
            )
        )

        viewPager2.adapter = OnBoardingViewPagerAdapter(pageList)

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position<3) {
                    btnNext.text = getString(R.string.next_step)
                }
                else {
                    btnNext.text= getString(R.string.begin)
                }
            }
        })

        dotsIndicator.setViewPager2(viewPager2)

        btnSkip.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        btnNext.setOnClickListener {
            if (viewPager2.currentItem < 3) {
                viewPager2.currentItem = viewPager2.currentItem + 1
            }
            else {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }
    }
}