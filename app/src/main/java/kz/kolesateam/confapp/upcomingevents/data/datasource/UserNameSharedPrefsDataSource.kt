package kz.kolesateam.confapp.upcomingevents.data.datasource

import android.content.SharedPreferences

private const val USER_NAME = "user_name"
private const val DEFAULT_USER_NAME = "Гость"

class UserNameSharedPrefsDataSource(
    private val sharedPreferences: SharedPreferences,
) : UserNameDataSource {
    override fun getUserName(): String =
        sharedPreferences.getString(USER_NAME, DEFAULT_USER_NAME) ?: DEFAULT_USER_NAME

    override fun saveUserName(
        userName: String,
    ) {
        sharedPreferences.edit().putString(USER_NAME, userName).apply()
    }

    override fun isSavedUserName(): Boolean = sharedPreferences.contains(USER_NAME)
}