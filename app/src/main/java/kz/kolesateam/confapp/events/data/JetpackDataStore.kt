package kz.kolesateam.confapp.events.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val USER_NAME_DATA_STORE = "user_name_data_store"


class JetpackDataStore(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = USER_NAME_DATA_STORE)

    companion object {
        val USER_NAME_KEY = preferencesKey<String>("DOWNLOADED_DATA_KEY")
    }

    suspend fun saveData(userName: String) {
        dataStore.edit {preferences ->
            preferences[USER_NAME_KEY] = userName
        }
    }


    val userNameFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_NAME_KEY] ?: ""
    }

}