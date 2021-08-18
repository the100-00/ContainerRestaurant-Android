package container.restaurant.android.presentation.feed.write

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.naver.maps.geometry.Tm128
import container.restaurant.android.R
import container.restaurant.android.data.db.entity.MainFood
import container.restaurant.android.data.db.entity.SideDish
import container.restaurant.android.data.request.FeedWriteRequest
import container.restaurant.android.data.response.ImageUploadResponse
import container.restaurant.android.databinding.ActivityFeedWriteBinding
import container.restaurant.android.dialog.AlertDialog
import container.restaurant.android.dialog.SimpleConfirmDialog
import container.restaurant.android.presentation.base.BaseActivity
import container.restaurant.android.util.CommUtils
import container.restaurant.android.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.InputStream

class FeedWriteActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFeedWriteBinding
    private val viewModel: FeedWriteViewModel by viewModel()
    private val bottomSheetFragment = FeedWriteBottomSheetFragment()

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

//                        observe(viewModel.uploadImg(bmpFile), ::imgUploadComplete)

                        Glide.with(this).load(bmp).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(binding.ivUploadImage)
                        binding.ivUploadImage.visibility = View.VISIBLE
                        binding.ivDeleteImage.visibility = View.VISIBLE
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
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        observeData()
        setBindItem()
        subscribeUi()
        difficultyAction()
        setUpRecyclerViewCategorySelection()
    }

    // 음식 카테고리 메뉴를 폰 기종마다 다르게(길이가 부족하면 버튼이 다음줄로 넘어가게) 하기 위한 설정정
   private fun setUpRecyclerViewCategorySelection() {
        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            binding.rvCategory.layoutManager = it
        }
    }

    private fun observeData() {
        with(viewModel){
            isBackButtonClicked.observe(this@FeedWriteActivity, EventObserver {
                if(it) {
                    onBackPressed()
                }
            })
            isWelcomedButtonClicked.observe(this@FeedWriteActivity , EventObserver {
                Timber.d("onWelcomeButtonClick $it")
                if(it) {
                    binding.badgeFilled.visibility = View.VISIBLE
                    binding.ivBadgeUnfilled.visibility = View.INVISIBLE
                    binding.badgeFilled.playAnimation()
                }
                else {
                    binding.badgeFilled.visibility = View.INVISIBLE
                    binding.ivBadgeUnfilled.visibility = View.VISIBLE
                }
            })

        }
    }

    override fun onBackPressed() {
        val dialog = SimpleConfirmDialog(
            "작성을 종료할까요?",
            "지금까지 작성한 내용은 저장되지 않아요",
            "취소",
            "확인"
        )
        dialog.setMultiEventListener(object: SimpleConfirmDialog.MultiEventListener {
            override fun onLeftBtnClick(dialogSelf: SimpleConfirmDialog) {
                dialog.dismiss()
            }

            override fun onRightBtnClick(dialogSelf: SimpleConfirmDialog) {
                finish()
            }
        })
        dialog.show(supportFragmentManager,"SimpleConfirmDialog")
    }

    private fun setBindItem() {
        binding.apply {
            etSearchContainer.setOnClickListener(this@FeedWriteActivity)
            llGetPicture.setOnClickListener(this@FeedWriteActivity)
            ivDeleteImage.setOnClickListener(this@FeedWriteActivity)
            btnFeedUpdate.setOnClickListener(this@FeedWriteActivity)
        }
    }

    private fun subscribeUi() {
        with(viewModel) {
//            observe(viewLoading, ::loadingCheck)
//            observe(getErrorMsg, ::errorDialog)
//
//            observe(mainFoodList, ::getMainFoodList)
//            observe(sideDishList, ::getSideDishList)
//            observe(loginChk.asLiveData()) { updateFeed(it) }
        }
    }

    private fun updateFeed(chk: Boolean) {
        if(chk) {
//            viewModel.loginChk.value = false

            if(feedNullCheck()) {
                val restaurantCreateDto = restaurantCreateDto
                val category = categoryStr
                val difficulty = binding.sbDifficulty.childCount
                val welcome = welcome
                val thumbnailImageId = imageUploadId ?: 9
                val content = binding.etContent.text.toString()
                val mainMenuList: MutableList<FeedWriteRequest.MainMenu> = mutableListOf()
//                mainFoodAdapter.currentList.forEach {
//                    mainMenuList.add(FeedWriteRequest.MainMenu(it.foodName, it.bottle))
//                }
                val subMenuList: MutableList<FeedWriteRequest.SubMenu> = mutableListOf()
//                sideDishAdapter.currentList.forEach {
//                    subMenuList.add(FeedWriteRequest.SubMenu(it.quantity, it.bottle))
//                }

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

//                observe(viewModel.updateFeed(feedWriteRequest), ::feedWriteComplete)
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
                finish()
            }
        })
        if(!AlertDialog.showCheck)
            dialog.show()

    }


    private fun getMainFoodList(list: List<MainFood>) {
//        binding.rvMainFood.adapter = mainFoodAdapter
//        mainFoodAdapter.submitList(list)
    }

    private fun getSideDishList(list: List<SideDish>) {
//        binding.rvSideDish.adapter = sideDishAdapter
//        sideDishAdapter.submitList(list)
    }

    private fun onDeleteImage(){
        binding.tvImageCount.text = "0/1"
        binding.ivUploadImage.setImageResource(0)
        binding.ivDeleteImage.visibility = View.GONE
    }

    private fun onClickNameSearch() {
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }


    private fun difficultyAction() {
        binding.sbDifficulty.setOnRatingChangeListener { RatingCount ->
            binding.tvDifficultyInfo.visibility = View.VISIBLE
            when (RatingCount.toInt()) {
                0 -> binding.tvDifficultyInfo.visibility = View.INVISIBLE
                1 -> binding.tvDifficultyInfo.text = "쉬워요"
                2 -> binding.tvDifficultyInfo.text = "할 만 해요"
                3 -> binding.tvDifficultyInfo.text = "보통이에요"
                4 -> binding.tvDifficultyInfo.text = "까다로워요"
                5 -> binding.tvDifficultyInfo.text = "많이 어려워요"
            }
        }
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
            binding.llGetPicture -> dispatchAlbumIntent()
            binding.ivDeleteImage -> onDeleteImage()
//            binding.btnFeedUpdate -> observe(viewModel.tempLogin()) {}
        }
    }


    companion object {
        fun getIntent(activity: AppCompatActivity) = Intent(activity, FeedWriteActivity::class.java)
    }
}