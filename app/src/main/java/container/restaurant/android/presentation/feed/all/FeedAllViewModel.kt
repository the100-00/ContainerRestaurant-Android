package container.restaurant.android.presentation.feed.all

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class FeedAllViewModel : ViewModel() {

    val close = MutableLiveData<Unit>()
    val showHelpDialog = MutableLiveData<Unit>()
    val navToUserProfile = MutableLiveData<FeedUser>()

    fun onClickUser(user: FeedUser) {
        navToUserProfile.value = user
    }

    fun onClickClose() {
        close.value = Unit
    }

    fun onClickHelp() {
        showHelpDialog.value = Unit
    }
}