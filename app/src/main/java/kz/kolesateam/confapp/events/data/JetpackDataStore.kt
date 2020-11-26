package kz.kolesateam.confapp.events.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
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