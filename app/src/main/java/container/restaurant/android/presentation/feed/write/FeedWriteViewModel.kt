package container.restaurant.android.presentation.feed.write

import androidx.lifecycle.*
import container.restaurant.android.data.Category
import container.restaurant.android.data.CategorySelection
import container.restaurant.android.data.MainMenu
import container.restaurant.android.data.SubMenu
import container.restaurant.android.data.repository.FeedWriteRepository
import container.restaurant.android.util.Event
import container.restaurant.android.util.RecyclerViewItemClickListeners
import okhttp3.internal.notify
import timber.log.Timber

class FeedWriteViewModel(private val feedWriteRepository: FeedWriteRepository) : ViewModel(), RecyclerViewItemClickListeners.CategorySelectionItemClickListener {
    val getErrorMsg = MutableLiveData<String>()
    val viewLoading = MutableLiveData<Boolean>()

    private val _mainMenuList: MutableLiveData<MutableList<MainMenu>> = MutableLiveData(
        mutableListOf()
    )
    val mainMenuList: LiveData<MutableList<MainMenu>> = _mainMenuList

    private val _subMenuList: MutableLiveData<MutableList<SubMenu>> = MutableLiveData(
        mutableListOf()
    )
    val subMenuList: LiveData<MutableList<SubMenu>> = _subMenuList

    private val _isBackButtonClicked:MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isBackButtonClicked:LiveData<Event<Boolean>> = _isBackButtonClicked

    val categoryList = mutableListOf(
        CategorySelection(Category.KOREAN),
        CategorySelection(Category.NIGHT_MEAL),
        CategorySelection(Category.CHINESE),
        CategorySelection(Category.SCHOOL_FOOD),
        CategorySelection(Category.FAST_FOOD),
        CategorySelection(Category.ASIAN_AND_WESTERN),
        CategorySelection(Category.COFFEE_AND_DESSERT),
        CategorySelection(Category.JAPANESE),
        CategorySelection(Category.CHICKEN_AND_PIZZA)
    )

    var selectedCategory: Category? = null

    fun onBackButtonClick() {
        _isBackButtonClicked.value = Event(true)
    }

    fun onAddMainMenuButtonClick() {
        _mainMenuList.value?.add(MainMenu())
        _mainMenuList.value = _mainMenuList.value
    }

    fun onAddSubMenuButtonClick() {
        _subMenuList.value?.add(SubMenu())
        _subMenuList.value = _subMenuList.value
    }

    override fun onClick(item: CategorySelection, adapterPosition: Int) {
        Timber.d("$item, $adapterPosition")
        if(item.checked.value!=null) {
            //이미 체크가 되어있다면 체크 해제
            if(item.checked.value!!){
                item.checked.value = false
                selectedCategory = null
            }
            //그렇지 않다면 다른 것이 선택되있거나 아무것도 선택되지 않은 상태 => 지금 아이템만 체크 상태로 변경
            else {
                categoryList.forEachIndexed { index, categorySelection ->
                    categorySelection.checked.value = (index == adapterPosition)
                    if(index == adapterPosition) selectedCategory = categorySelection.category
                }
            }
        }
    }

//    val mainFoodList = feedWriteRepository.getMainFoodList().asLiveData()
//    val sideDishList = feedWriteRepository.getSideDishList().asLiveData()

//    fun addMainFood(list: List<MainFood>) = feedWriteRepository.addMainFood(list).asLiveData()
//    fun addSideDish(list: List<SideDish>) = feedWriteRepository.addSideDish(list).asLiveData()
//
//    val searchProgressChk = MutableStateFlow(false)
//    fun getSearchPlace(place: String) = feedWriteRepository.getSearchPlace(
//        place = place,
//        onStart = { searchProgressChk.value = true },
//        onComplete = { searchProgressChk.value = false },
//        onError = {getErrorMsg.postValue(it)}
//
//    ).asLiveData()
//
//    val loginChk = MutableStateFlow(false)
//
//    fun tempLogin() = feedWriteRepository.tempLogin (
//        onStart = { viewLoading.postValue(true) },
//        onComplete = {viewLoading.postValue(false); loginChk.value = true},
//        onError = { getErrorMsg.postValue(it) }
//    ).asLiveData()
//
//    fun updateFeed(feedWriteRequest: FeedWriteRequest) = feedWriteRepository.updateFeed(
//        feedWriteRequest= feedWriteRequest,
//        onStart = { viewLoading.postValue(true) },
//        onComplete = { viewLoading.postValue(false) },
//        onError = {getErrorMsg.postValue(it)}
//    ).asLiveData()
//
//    fun uploadImg(bmpFile: File) = feedWriteRepository.uploadImg(
//        bmpFile = bmpFile,
//        onStart = {viewLoading.postValue(true)},
//        onComplete = {viewLoading.postValue(false)},
//        onError = {getErrorMsg.postValue(it)}
//    ).asLiveData()
}