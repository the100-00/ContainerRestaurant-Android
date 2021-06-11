package container.restaurant.android.presentation.feed.write

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.naver.maps.geometry.Tm128
import container.restaurant.android.R
import container.restaurant.android.presentation.base.BaseActivity
import container.restaurant.android.data.db.MainFood
import container.restaurant.android.data.db.SideDish
import container.restaurant.android.data.request.FeedWriteRequest
import container.restaurant.android.data.response.ImageUploadResponse
import container.restaurant.android.databinding.ActivityFeedWriteBinding
import container.restaurant.android.dialog.AlertDialog
import container.restaurant.android.presentation.MainActivity
import container.restaurant.android.presentation.feed.write.adapter.MainFoodAdapter
import container.restaurant.android.presentation.feed.write.adapter.SideDishAdapter
import container.restaurant.android.util.CommUtils
import container.restaurant.android.util.hide
import container.restaurant.android.util.observe
import container.restaurant.android.util.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.InputStream

class FeedWriteActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFeedWriteBinding
    private val viewModel: FeedWriteViewModel by viewModel()
    private val bottomSheetFragment = FeedWriteBottomSheetFragment {

        val tm128 = Tm128(it.mapx.toDouble(), it.mapy.toDouble())
        val latLng = tm128.toLatLng()

        restaurantCreateDto = FeedWriteRequest.RestaurantCreateDto(name = it.title, address = it.address, latitude = latLng.latitude, longitude = latLng.longitude)
        binding.etSearchContainer.text = CommUtils.fromHtml(it.title)
    }
    private val mainFoodAdapter = MainFoodAdapter()
    private val sideDishAdapter = SideDishAdapter()

    private var restaurantCreateDto: FeedWriteRequest.RestaurantCreateDto? = null
    private var categoryStr = "CHINESE"
    private var imageUploadId: Int? = null
    private var welcome = false

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data = result.data

            data?.data?.let { imgUri ->
                if(imgUri.toString().contains("content:", true)) {
                    try {
                        val inputStream: InputStream = contentResolver.openInputStream(imgUri)!!
                        val bmp: Bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream.close()
                        val bmpFile = CommUtils.convertBitmapToFile(this, "userImg.jpeg", bmp)

                        observe(viewModel.uploadImg(bmpFile), ::imgUploadComplete)

                        Glide.with(this).load(bmp).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(binding.ivUploadImage)
                        binding.ivUploadImage.show()
                        binding.ivDeleteImage.show()
                        binding.tvImageCount.text = "1/1"

                    } catch (ex: Exception) {
                        simpleDialog("Error", ex.message.toString(), AlertDialog.ERROR_TYPE)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed_write)

        setupToolbar()
        setBindItem()
        setCategory()
        subscribeUi()
        difficultyAction()
    }

    private fun setBindItem() {
        binding.apply {
            etSearchContainer.setOnClickListener(this@FeedWriteActivity)
            tvAddMainMenu.setOnClickListener(this@FeedWriteActivity)
            tvAddSideMenu.setOnClickListener(this@FeedWriteActivity)
            llGetPicture.setOnClickListener(this@FeedWriteActivity)
            ivDeleteImage.setOnClickListener(this@FeedWriteActivity)
            clIsLike.setOnClickListener(this@FeedWriteActivity)
            btnFeedUpdate.setOnClickListener(this@FeedWriteActivity)
            ivBack.setOnClickListener(this@FeedWriteActivity)
        }
    }

    private fun subscribeUi() {
        with(viewModel) {
            observe(viewLoading, ::loadingCheck)
            observe(getErrorMsg, ::errorDialog)

            observe(mainFoodList, ::getMainFoodList)
            observe(sideDishList, ::getSideDishList)
            observe(loginChk.asLiveData()) { updateFeed(it) }
        }
    }

    private fun updateFeed(chk: Boolean) {
        if(chk) {
            viewModel.loginChk.value = false

            if(feedNullCheck()) {
                val restaurantCreateDto = restaurantCreateDto
                val category = categoryStr
                val difficulty = binding.sbDifficulty.progress
                val welcome = welcome
                val thumbnailImageId = imageUploadId ?: 9
                val content = binding.etContent.text.toString()
                val mainMenuList: MutableList<FeedWriteRequest.MainMenu> = mutableListOf()
                mainFoodAdapter.currentList.forEach {
                    mainMenuList.add(FeedWriteRequest.MainMenu(it.foodName, it.bottle))
                }
                val subMenuList: MutableList<FeedWriteRequest.SubMenu> = mutableListOf()
                sideDishAdapter.currentList.forEach {
                    subMenuList.add(FeedWriteRequest.SubMenu(it.quantity, it.bottle))
                }

                val feedWriteRequest = FeedWriteRequest(
                    restaurantCreateDto= restaurantCreateDto!!,
                    category= category,
                    difficulty= difficulty,
                    welcome= welcome,
                    thumbnailImageId= thumbnailImageId,
                    content= content,
                    mainMenu= mainMenuList,
                    subMenu= subMenuList
                )

                observe(viewModel.updateFeed(feedWriteRequest), ::feedWriteComplete)
            }
        }
    }

    private fun feedNullCheck(): Boolean {
        if(restaurantCreateDto == null) {
            simpleDialog("Warning", "식당을 선택해주세요", AlertDialog.WARNING_TYPE)
            return false
        }
        return true
    }

    private fun feedWriteComplete(str: String) {
        val dialog = AlertDialog(this, "Success", str, "Confirm", null, AlertDialog.SUCCESS_TYPE)
        dialog.setSingleEventListener(object : AlertDialog.SingleEventListener {
            override fun confirmClick(dialogSelf: AlertDialog) {
                dialogSelf.dismiss()
                gotoMain()
            }
        })
        if(!AlertDialog.showCheck)
            dialog.show()

    }

    private fun setCategory() {
        binding.radioGroup.setOnCheckedChangeListener { p0, p1 ->
            when(p1) {
                R.id.rb_asian_food -> categoryStr = "ASIAN_AND_WESTERN"
                R.id.rb_chiken -> categoryStr = "CHICKEN_AND_PIZZA"
                R.id.rb_china_food -> categoryStr = "CHINESE"
                R.id.rb_desert -> categoryStr = "COFFEE_AND_DESSERT"
                R.id.rb_fast_food -> categoryStr = "FAST_FOOD"
                R.id.rb_japan -> categoryStr = "JAPANESE"
                R.id.rb_korea_food -> categoryStr = "KOREAN"
                R.id.rb_midnight_meal -> categoryStr = "NIGHT_MEAL"
                R.id.rb_school_food -> categoryStr = "SCHOOL_FOOD"
            }
        }
    }

    private fun getMainFoodList(list: List<MainFood>) {
        binding.rvMainFood.adapter = mainFoodAdapter
        mainFoodAdapter.submitList(list)
    }

    private fun getSideDishList(list: List<SideDish>) {
        binding.rvSideDish.adapter = sideDishAdapter
        sideDishAdapter.submitList(list)
    }

    private fun onDeleteImage(){
        binding.tvImageCount.text = "0/1"
        binding.ivUploadImage.setImageResource(0)
        binding.ivDeleteImage.visibility = View.GONE
    }

    private fun isLikeAction() {
        binding.ivBadgeUnfilled.isSelected = binding.ivBadgeUnfilled.isSelected != true
        welcome = binding.ivBadgeUnfilled.isSelected

    }

    private fun onClickNameSearch() {
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun difficultyAction() {
        binding.sbDifficulty.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvDifficultyInfo.show()
                when(progress) {
                    0 -> binding.tvDifficultyInfo.hide()
                    1 -> binding.tvDifficultyInfo.text = "쉬워요"
                    2 -> binding.tvDifficultyInfo.text = "할 만 해요"
                    3 -> binding.tvDifficultyInfo.text = "보통이에요"
                    4 -> binding.tvDifficultyInfo.text = "까다로워요"
                    5 -> binding.tvDifficultyInfo.text = "많이 어려워요"
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
    }

    private fun dispatchAlbumIntent() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        resultLauncher.launch(intent)
    }

    private fun imgUploadComplete(uploadResponse: ImageUploadResponse) {
        imageUploadId = uploadResponse.id
        simpleDialog("Success", "이미지 등록 완료", AlertDialog.SUCCESS_TYPE)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.etSearchContainer -> onClickNameSearch()
            binding.tvAddMainMenu -> observe(viewModel.addMainFood(mainFoodAdapter.currentList)) {}
            binding.tvAddSideMenu -> observe(viewModel.addSideDish(sideDishAdapter.currentList)) {}
            binding.llGetPicture -> dispatchAlbumIntent()
            binding.ivDeleteImage -> onDeleteImage()
            binding.clIsLike -> isLikeAction()
            binding.btnFeedUpdate -> observe(viewModel.tempLogin()) {}
            binding.ivBack -> gotoMain()
        }
    }

    private fun gotoMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        fun getIntent(activity: AppCompatActivity) = Intent(activity, FeedWriteActivity::class.java)
    }
}