package container.restaurant.android.presentation.my

import androidx.lifecycle.ViewModel
import container.restaurant.android.util.SingleLiveEvent

internal class MyViewModel : ViewModel() {

    val navToSettings = SingleLiveEvent<Unit>()

    fun onClickSettings() {
        navToSettings.value = Unit
    }
}