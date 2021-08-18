package container.restaurant.android.data

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData

data class MainMenu(
    val menuName: MutableLiveData<String> = MutableLiveData(""),
    val container: MutableLiveData<String> = MutableLiveData("")
)

data class SubMenu(
    var menuName: MutableLiveData<String> = MutableLiveData(""),
    var container: MutableLiveData<String> = MutableLiveData("")
)

data class CategorySelection(
    var category: Category,
    var checked: MutableLiveData<Boolean> = MutableLiveData(false)
)

data class FoodPhoto(
    val bitmap: Bitmap
)