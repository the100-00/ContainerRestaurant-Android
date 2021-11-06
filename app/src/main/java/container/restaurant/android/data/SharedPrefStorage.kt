package container.restaurant.android.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PrefStorage {
    var isUserSignIn: Boolean
    var tokenBearer: String
    var userId: Int
    var isOnBoardingFirst: Boolean
}

internal class SharedPrefStorage(
    private val context: Context
) : PrefStorage {

    private val prefs: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    }

    override var isUserSignIn by BooleanPreference(prefs, IS_USER_LOGIN)
    override var userId by IntPreference(prefs, USER_ID)
    override var tokenBearer by StringPreference(prefs, TOKEN_BEARER)
    override var isOnBoardingFirst by BooleanPreference(prefs, IS_ON_BOARDING_FIRST, true)

    companion object {
        private const val PREFS_NAME = "container-android"
        private const val IS_USER_LOGIN = "IS_USER_LOGIN"
        private const val TOKEN_BEARER = "TOKEN_BEARER"
        private const val USER_ID =  "USER_ID"
        private const val IS_ON_BOARDING_FIRST = "IS_ON_BOARDING_FIRST"
    }
}

internal class BooleanPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Boolean = false
) : ReadWriteProperty<Any, Boolean> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.value.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.value.edit { putBoolean(name, value) }
    }

}

internal class IntPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Int = 0
) :ReadWriteProperty<Any, Int> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return preferences.value.getInt(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        preferences.value.edit { putInt(name, value) }
    }
}

internal class StringPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: String = ""
) : ReadWriteProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return preferences.value.getString(name, defaultValue)!!
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        return preferences.value.edit { putString(name, value) }
    }
}