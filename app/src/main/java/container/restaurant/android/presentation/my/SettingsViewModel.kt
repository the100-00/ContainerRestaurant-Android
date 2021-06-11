package container.restaurant.android.presentation.my

import androidx.lifecycle.ViewModel
import container.restaurant.android.util.SingleLiveEvent

internal class SettingsViewModel : ViewModel() {

    val back = SingleLiveEvent<Unit>()

    fun onClickBack() {
        back.call()
    }
}
