package container.restaurant.android.data

import androidx.lifecycle.MutableLiveData

data class MainMenu(
    val menuName: MutableLiveData<String> = MutableLiveData(""),
    val container: MutableLiveData<String> = MutableLiveData("")
)

data class SubMenu(
    var menuName: MutableLiveData<String> = MutableLiveData(""),
    var container: MutableLiveData<String> = MutableLiveData("")
)