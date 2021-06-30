package container.restaurant.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import container.restaurant.android.presentation.auth.AuthViewModelDelegate
import container.restaurant.android.util.Event

internal class HomeViewModel(
    private val authViewModelDelegate: AuthViewModelDelegate
) : ViewModel(), AuthViewModelDelegate by authViewModelDelegate {

    val navToAllContainerFeed = MutableLiveData<Event<Unit>>()

    private val _isNavToAllContainerFeedClicked = MutableLiveData<Event<Boolean>>()
    val isNavToAllContainerFeedClicked : LiveData<Event<Boolean>> = _isNavToAllContainerFeedClicked

    private val _isNavToMyContainerFeedClicked = MutableLiveData<Event<Boolean>>()
    val isNavToMyContainerFeedClicked : LiveData<Event<Boolean>> = _isNavToMyContainerFeedClicked

    fun onClickMyContainerFeed() {
        _isNavToMyContainerFeedClicked.value = Event(true)
    }

    fun onClickAllContainerFeed() {
        _isNavToAllContainerFeedClicked.value = Event(true)
    }
}