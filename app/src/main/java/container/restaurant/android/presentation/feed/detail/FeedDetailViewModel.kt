package container.restaurant.android.presentation.feed.detail

import androidx.lifecycle.*
import com.tak8997.github.domain.ResultState
import container.restaurant.android.R
import container.restaurant.android.data.model.FeedComment
import container.restaurant.android.data.model.FeedDetail
import container.restaurant.android.data.model.PostComment
import container.restaurant.android.data.repository.FeedRepository
import container.restaurant.android.util.SingleLiveEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

internal class FeedDetailViewModel(
    private val feedId: Long = -1L,
    private val repository: FeedRepository
) : ViewModel() {

    val errorToast = SingleLiveEvent<Int>()
    val feedDetail = MutableLiveData<FeedDetail>()
    val feedComments = MutableLiveData<List<FeedComment>>(emptyList())
    val feedComment = MutableLiveData<FeedComment>()
    val level = feedDetail.map {
        it.difficulty
    }
    val mainMenu = feedDetail.map {
        it.mainMenu
    }
    val subMenu = feedDetail.map {
        it.subMenu
    }
    val feedLike = MutableLiveData(false)
    val feedScrap = MutableLiveData(false)
    val commentEditing = MutableLiveData<String>("")

    init {
        fetchFeedDetail(feedId)
    }

    fun onClickPostComment() {
        if (commentEditing.value.isNullOrEmpty()) {
            return
        }

        viewModelScope.launch {
            val postFeedResult = repository.postFeedComment(feedId, PostComment(commentEditing.value ?: ""))
            if (postFeedResult is ResultState.Success) {
                feedComment.value = postFeedResult.data ?: return@launch
            } else {
                errorToast.value = R.string.error_comment_post
            }
        }
    }

    fun onClickFeedLike() {
        viewModelScope.launch {
            val likeResult = if (feedLike.value == true) {
                repository.removeFeedLike(feedId)
            } else {
                repository.postFeedLike(feedId)
            }

            if (likeResult is ResultState.Success) {
                if (likeResult.data.links != null) {
                    feedLike.value = true
                }
            }
        }
    }

    fun onClickFeedScrap() {
        viewModelScope.launch {
            val scrapResult = if (feedScrap.value == true) {
                repository.removeFeedScrap(feedId)
            } else {
                repository.postFeedScrap(feedId)
            }

            if (scrapResult is ResultState.Success) {
                if (scrapResult.data.links != null) {
                    feedScrap.value = true
                }
            }
        }
    }

    private fun fetchFeedDetail(feedId: Long) {
        if (feedId == -1L) {
            errorToast.value = R.string.error_feed_detail
            return
        }

        viewModelScope.launch {
            val feedDetailAsync = async { repository.fetchFeedDetail(feedId) }
            val feedCommentAsync = async { repository.fetchFeedComment(feedId) }

            val feedDetailResult = feedDetailAsync.await()
            if (feedDetailResult is ResultState.Success) {
                val feedDetailData = feedDetailResult.data ?: return@launch
                feedDetail.value = feedDetailData
                feedLike.value = feedDetailData.isLike
                feedScrap.value = feedDetailData.isScraped
                //TODO: 유저프로필 다시 호출
            } else {
                errorToast.value = R.string.error_feed_detail
            }

            val feedCommentResult = feedCommentAsync.await()
            if (feedCommentResult is ResultState.Success) {
                feedComments.value = feedCommentResult.data ?: return@launch
            } else {
                errorToast.value = R.string.error_comment_post
            }
        }
    }
}