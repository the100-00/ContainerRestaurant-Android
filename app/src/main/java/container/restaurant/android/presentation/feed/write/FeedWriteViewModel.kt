package container.restaurant.android.presentation.feed.write

import androidx.lifecycle.*
import container.restaurant.android.data.MainMenu
import container.restaurant.android.data.SubMenu
import container.restaurant.android.data.repository.FeedWriteRepository
import container.restaurant.android.util.Event
import okhttp3.internal.notify
import timber.log.Timber

class FeedWriteViewModel(private val feedWriteRepository: FeedWriteRepository) : ViewModel() {
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

    //    private val _isAddMainMenuButtonClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
//    val isAddMainMenuButtonClicked:LiveData<Event<Boolean>> = _isAddMainMenuButtonClicked
//
//    private val _isAddSubMenuButtonClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
//    val isAddSubMenuButtonClicked:LiveData<Event<Boolean>> = _isAddSubMenuButtonClicked
//
    fun onAddMainMenuButtonClick() {
        Timber.d("onAddMainMenuButtonClick")
        _mainMenuList.value?.add(MainMenu())
        _mainMenuList.value = _mainMenuList.value
    }

    fun onAddSubMenuButtonClick() {
        _subMenuList.value?.add(SubMenu())
        _subMenuList.value = _subMenuList.value
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