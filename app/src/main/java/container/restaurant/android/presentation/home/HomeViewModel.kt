package container.restaurant.android.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import container.restaurant.android.presentation.auth.AuthViewModelDelegate
import container.restaurant.android.util.Event

internal class HomeViewModel(
    private val authViewModelDelegate: AuthViewModelDelegate
) : ViewModel(), AuthViewModelDelegate by authViewModelDelegate {

    val navToMyContainerFeed = MutableLiveData<Event<Unit>>()
    val navToAllContainerFeed = MutableLiveData<Event<Unit>>()
    val kakaoLoginDialog = MutableLiveData<Event<Unit>>()

    fun onClickMyContainerFeed() {
        if (!isUserSignIn()) {
            kakaoLoginDialog.value = Event(Unit)
            return
        }
    }

    fun onClickAllContainerFeed() {
        navToAllContainerFeed.value = Event(Unit)
    }
}