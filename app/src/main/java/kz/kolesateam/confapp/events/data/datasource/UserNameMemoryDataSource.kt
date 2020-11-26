package kz.kolesateam.confapp.events.data.datasource

private const val DEFAULT_USER_NAME = "Гость"

class UserNameMemoryDataSource : UserNameDataSource {
    private var userName: String? = null

    override fun getUserName(): String = userName ?: DEFAULT_USER_NAME

    override fun saveUserName(userName: String) {
        this.userName = userName
    }
}