package container.restaurant.android.presentation.feed.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import container.restaurant.android.data.repository.HomeRepository
import container.restaurant.android.data.response.AllCourageResponse
import container.restaurant.android.util.SingleLiveEvent
import container.restaurant.android.util.handleApiResponse
import kotlinx.coroutines.flow.collect

internal class FeedAllViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _latestWriters = MutableLiveData<List<AllCourageResponse.CourageResponse>>()
    val latestWriters: LiveData<List<AllCourageResponse.CourageResponse>> = _latestWriters

    private val _topWriters = MutableLiveData<List<AllCourageResponse.CourageResponse>> ()
    val topWriters: LiveData<List<AllCourageResponse.CourageResponse>> = _topWriters

    private val _writerCount = MutableLiveData<Int>()
    val writerCount:LiveData<Int> = _writerCount

    private val _feedCount = MutableLiveData<Int>()
    val feedCount: LiveData<Int> = _feedCount

    private val _isCloseClicked = SingleLiveEvent<Void>()
    val isCloseClicked: LiveData<Void> = _isCloseClicked

    fun onClickClose() {
        _isCloseClicked.call()
    }

    private val _isHelpClicked = SingleLiveEvent<Void>()
    val isHelpClicked: LiveData<Void> = _isHelpClicked

    fun onClickHelp() {
        _isHelpClicked.call()
    }

    val navToUserProfile = MutableLiveData<FeedUser>()

    fun onClickUser(user: FeedUser) {
        navToUserProfile.value = user
    }

    suspend fun getAllContainer() {
        homeRepository.getAllCourage()
            .collect { response ->
                handleApiResponse(
                    response = response,
                    onSuccess = {
                        it.data?.let{ allCourageResponse ->
                            _latestWriters.value = allCourageResponse.latestWriters
                            _topWriters.value = allCourageResponse.topWriters
                            _writerCount.value = allCourageResponse.writerCount
                            _feedCount.value = allCourageResponse.feedCount
                        }
                    }
                )
            }
    }
}