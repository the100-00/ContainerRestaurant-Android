package container.restaurant.android.presentation.feed.all

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class FeedAllViewModel : ViewModel() {

    val close = MutableLiveData<Unit>()
    val showHelpDialog = MutableLiveData<Unit>()

    fun onClickClose() {
        close.value = Unit
    }

    fun onClickHelp() {
        showHelpDialog.value = Unit
    }
}