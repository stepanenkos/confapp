package kz.kolesateam.confapp.upcomingevents.data.datasource

interface UserNameDataSource {

    fun getUserName(): String

    fun saveUserName(
        userName: String
    )

    fun isSavedUserName(): Boolean
}