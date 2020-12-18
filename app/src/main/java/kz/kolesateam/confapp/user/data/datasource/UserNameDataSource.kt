package kz.kolesateam.confapp.user.data.datasource

interface UserNameDataSource {

    fun getUserName(): String

    fun saveUserName(
        userName: String
    )

    fun isSavedUserName(): Boolean
}