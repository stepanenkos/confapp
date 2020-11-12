package kz.kolesateam.confapp.events.data

import android.content.Context
import android.widget.TextView
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.kolesateam.confapp.R

const val DOWNLOADED_DATA_KEY = "downloaded_data"


class JetpackDataStore(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = DOWNLOADED_DATA_KEY)

    companion object {
        val DATA_KEY = preferencesKey<String>("DOWNLOADED_DATA_KEY")
        val COLOR_TEXT_VIEW_KEY = preferencesKey<Int>("COLOR_TEXT_VIEW_KEY")
    }

    suspend fun saveData(data: String, textColor: Int) {
        dataStore.edit {preferences ->
            preferences[DATA_KEY] = data
            preferences[COLOR_TEXT_VIEW_KEY] = textColor
        }
    }


    val downloadedDataFlow: Flow<String> = dataStore.data.map {preferences ->
        preferences[DATA_KEY] ?: ""
    }

    val colorTextFlow: Flow<Int> = dataStore.data.map { preferences ->
        preferences[COLOR_TEXT_VIEW_KEY] ?: R.color.activity_upcoming_events_error_text_view
    }

    var TextView.textColor: Int
        get() = currentTextColor
        set(v) = setTextColor(v)
}