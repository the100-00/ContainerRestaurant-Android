package container.restaurant.android.presentation.feed.write

import androidx.lifecycle.*
import container.restaurant.android.data.*
import container.restaurant.android.data.repository.FeedWriteRepository
import container.restaurant.android.data.response.FeedListResponse
import container.restaurant.android.data.response.SearchLocationResponse
import container.restaurant.android.util.Event
import container.restaurant.android.util.RecyclerViewItemClickListeners
import container.restaurant.android.util.handleApiResponse
import kotlinx.coroutines.flow.collect
import okhttp3.internal.notify
import timber.log.Timber

class FeedWriteViewModel(private val feedWriteRepository: FeedWriteRepository) : ViewModel(),
    RecyclerViewItemClickListeners.CategorySelectionItemClickListener,
    RecyclerViewItemClickListeners.FoodPhotoItemClickListener,
    RecyclerViewItemClickListeners.SearchResultItemClickListener{
    val getErrorMsg = MutableLiveData<String>()
    val viewLoading = MutableLiveData<Boolean>()

    private val _mainMenuList: MutableLiveData<MutableList<MainMenu>> = MutableLiveData(
        mutableListOf(MainMenu())
    )
    val mainMenuList: LiveData<MutableList<MainMenu>> = _mainMenuList

    private val _subMenuList: MutableLiveData<MutableList<SubMenu>> = MutableLiveData(
        mutableListOf(SubMenu())
    )
    val subMenuList: LiveData<MutableList<SubMenu>> = _subMenuList

    val foodPhotoList: MutableLiveData<MutableList<FoodPhoto>> = MutableLiveData(
        mutableListOf()
    )

    private val _isAddPhotoButtonClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isAddPhotoButtonClicked: LiveData<Event<Boolean>> = _isAddPhotoButtonClicked

    private val _isBackButtonClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isBackButtonClicked: LiveData<Event<Boolean>> = _isBackButtonClicked

    private val _isWelcomedButtonClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isWelcomedButtonClicked: LiveData<Event<Boolean>> = _isWelcomedButtonClicked

    private val _isCloseSearchButtonClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isCloseSearchButtonClicked: LiveData<Event<Boolean>> = _isCloseSearchButtonClicked

    private val _isSearchButtonClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isSearchButtonClicked: LiveData<Event<Boolean>> = _isSearchButtonClicked

    private val _isSearchEditTextClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isSearchEditTextClicked: LiveData<Event<Boolean>> = _isSearchEditTextClicked

    private val _isSearchResultItemClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isSearchResultItemClicked: LiveData<Event<Boolean>> = _isSearchResultItemClicked

    private val _isErasePlaceNameClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isErasePlaceNameClicked: LiveData<Event<Boolean>> = _isErasePlaceNameClicked

    private val _isEraseSearchPlaceNameClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isEraseSearchPlaceNameClicked: LiveData<Event<Boolean>> = _isEraseSearchPlaceNameClicked

    private val _searchLocationList =
        MutableLiveData<List<SearchLocationResponse.Item>>()
    val searchLocationList: LiveData<List<SearchLocationResponse.Item>> = _searchLocationList

    // 식당 검색 결과 선택 시 바뀌는 이름
    private val _placeName: MutableLiveData<String> = MutableLiveData()
    val placeName: LiveData<String> = _placeName

    // 검색창에 입력이 일어날 때마다 바뀌는 이름
    val searchPlaceName: MutableLiveData<String> = MutableLiveData()

    var isWelcomed = false

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

    // 사용자가 선택한 음식 카테고리를 저장하는 변수. 선택되지 않았으면 null임
    var selectedCategory: Category? = null

    fun onAddPhotoButtonClick() {
        _isAddPhotoButtonClicked.value = Event(true)
    }

    fun onBackButtonClick() {
        _isBackButtonClicked.value = Event(true)
    }

    fun onWelcomedButtonClick() {
        isWelcomed = !isWelcomed
        _isWelcomedButtonClicked.value = Event(isWelcomed)
    }

    fun onCloseSearchButtonClick() {
        _isCloseSearchButtonClicked.value = Event(true)
    }

    fun onSearchButtonClick() {
        _isSearchButtonClicked.value = Event(true)
    }

    fun onSearchEditTextClick() {
        _isSearchEditTextClicked.value = Event(true)
    }

    fun onAddMainMenuButtonClick() {
        _mainMenuList.value?.add(MainMenu())
        _mainMenuList.value = _mainMenuList.value
    }

    fun onAddSubMenuButtonClick() {
        _subMenuList.value?.add(SubMenu())
        _subMenuList.value = _subMenuList.value
    }

    fun onErasePlaceNameClick() {
        _isErasePlaceNameClicked.value = Event(true)
    }

    fun onEraseSearchPlaceNameClick() {
        _isEraseSearchPlaceNameClicked.value = Event(true)
    }

    fun erasePlaceName() {
        _placeName.value = ""
    }

    fun makeSearchResultEmpty() {
        _searchLocationList.value = listOf()
    }


    suspend fun getSearchPlace(placeName: String) {
        feedWriteRepository.getSearchLocation(placeName)
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        _searchLocationList.value = it.data?.items
                        Timber.d("it.data.items : ${searchLocationList.value}")
                    },
                    onError = {
                        Timber.d("it.errorBody : ${it.errorBody}")
                        Timber.d("it.headers : ${it.headers}")
                        Timber.d("it.raw : ${it.raw}")
                        Timber.d("it.response : ${it.response}")
                        Timber.d("it.statusCode : ${it.statusCode}")
                    },
                    onException = {
                        Timber.d("it.message : ${it.message}")
                        Timber.d("it.exception : ${it.exception}")
                    }
                )
            }
    }

    override fun onCategorySelectionItemClick(item: CategorySelection, adapterPosition: Int) {
        Timber.d("$item, $adapterPosition")
        if (item.checked.value != null) {
            //이미 체크가 되어있다면 체크 해제
            if (item.checked.value!!) {
                item.checked.value = false
                selectedCategory = null
            }
            //그렇지 않다면 다른 것이 선택되있거나 아무것도 선택되지 않은 상태 => 지금 아이템만 체크 상태로 변경
            else {
                categoryList.forEachIndexed { index, categorySelection ->
                    categorySelection.checked.value = (index == adapterPosition)
                    if (index == adapterPosition) selectedCategory = categorySelection.category
                }
            }
        }
    }

    override fun onDeleteClick(adapterPosition: Int) {
        foodPhotoList.value?.removeAt(adapterPosition)
        foodPhotoList.value = foodPhotoList.value
    }

    override fun onSearchResultItemClick(item: SearchLocationResponse.Item) {
        _placeName.value = item.title
        _isSearchResultItemClicked.value = Event(true)
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