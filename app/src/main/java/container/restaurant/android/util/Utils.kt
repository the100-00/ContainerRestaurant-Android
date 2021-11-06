package container.restaurant.android.util

import android.content.Context
import androidx.lifecycle.MutableLiveData
import container.restaurant.android.R

fun setUserProfileResByLevelTitle(context: Context, userProfileRes: MutableLiveData<Int>, userLevelTitle: String?) {
    when(userLevelTitle) {
        context.getString(R.string.empty_profile_lv1) -> userProfileRes.value =
            R.drawable.empty_profile_lv1
        context.getString(R.string.empty_profile_lv2) -> userProfileRes.value =
            R.drawable.empty_profile_lv2
        context.getString(R.string.empty_profile_lv3) -> userProfileRes.value =
            R.drawable.empty_profile_lv3
        context.getString(R.string.empty_profile_lv4) -> userProfileRes.value =
            R.drawable.empty_profile_lv4
        context.getString(R.string.empty_profile_lv5) -> userProfileRes.value =
            R.drawable.empty_profile_lv5
    }
}

fun setHomeIconByLevelTitle(context: Context, homeIconResByUserLevel: MutableLiveData<Int>, userLevelTitle: String?) {
    when(userLevelTitle) {
        context.getString(R.string.empty_profile_lv1) -> homeIconResByUserLevel.value =
            R.drawable.ic_home_lv1
        context.getString(R.string.empty_profile_lv2) -> homeIconResByUserLevel.value =
            R.drawable.ic_home_lv2
        context.getString(R.string.empty_profile_lv3) -> homeIconResByUserLevel.value =
            R.drawable.ic_home_lv3
        context.getString(R.string.empty_profile_lv4) -> homeIconResByUserLevel.value =
            R.drawable.ic_home_lv4
        context.getString(R.string.empty_profile_lv5) -> homeIconResByUserLevel.value =
            R.drawable.ic_home_lv5
    }
}
