package container.restaurant.android.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PrefStorage {
    var isUserSignIn: Boolean
}

internal class SharedPrefStorage(
    private val context: Context
) : PrefStorage {

    private val prefs: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    }

    override var isUserSignIn by BooleanPreference(prefs, IS_USER_LOGIN, false)

    companion object {
        private const val PREFS_NAME = "container-android"
        private const val IS_USER_LOGIN = "IS_USER_LOGIN"
    }
}

internal class BooleanPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.value.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.value.edit { putBoolean(name, value) }
    }

}