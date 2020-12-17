package kz.kolesateam.confapp.favoriteevents.data

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.MapType
import kz.kolesateam.confapp.favoriteevents.domain.FavoritesRepository
import kz.kolesateam.confapp.models.EventData
import java.io.FileInputStream
import java.io.FileOutputStream
import kz.kolesateam.confapp.favoriteevents.domain.FavoriteEventActionObservable

private const val FAVORITE_EVENTS_FILE_NAME = "favorite_events.json"

class DefaultFavoritesRepository(
    private val context: Context,
    private val objectMapper: ObjectMapper,
    private val favoriteEventActionObservable: FavoriteEventActionObservable,
) : FavoritesRepository {
    private var favoriteEvents: MutableMap<Int, EventData> = mutableMapOf()

    init {
        val favoriteEventsFromFile = getFavoriteEventsFromFile()
        favoriteEvents.putAll(favoriteEventsFromFile)
    }

    override fun saveFavoriteEvent(eventData: EventData) {
        favoriteEvents[eventData.id] = eventData
        saveFavoriteEventsToFile()
        favoriteEventActionObservable.notifyChanged(
            eventId = eventData.id,
            isFavorite = true
        )
    }

    override fun removeFavoriteEvent(eventId: Int) {
        favoriteEvents.remove(eventId)
        saveFavoriteEventsToFile()
        favoriteEventActionObservable.notifyChanged(
            eventId = eventId,
            isFavorite = false
        )
    }

    override fun getAllFavoriteEvents(): List<EventData> {
        return favoriteEvents.values.toList()
    }

    override fun isFavorite(id: Int): Boolean = favoriteEvents.containsKey(id)


    private fun saveFavoriteEventsToFile() {
        val favoriteEventsJsonString: String = objectMapper.writeValueAsString(favoriteEvents)
        val fileOutputStream: FileOutputStream = context.openFileOutput(
            FAVORITE_EVENTS_FILE_NAME,
            Context.MODE_PRIVATE
        )
        fileOutputStream.write(favoriteEventsJsonString.toByteArray())
        fileOutputStream.close()
    }

    private fun getFavoriteEventsFromFile(): Map<Int, EventData> {
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = context.openFileInput(FAVORITE_EVENTS_FILE_NAME)
        } catch (exception: Exception) {
            fileInputStream?.close()
            return emptyMap()
        }
        val favoriteEventsJsonString: String =
            fileInputStream?.bufferedReader()?.readLines()?.joinToString().orEmpty()

        if (favoriteEventsJsonString.isEmpty() && favoriteEventsJsonString.isBlank()) return emptyMap()

        val mapType: MapType = objectMapper.typeFactory.constructMapType(
            Map::class.java,
            Int::class.java,
            EventData::class.java
        )
        return objectMapper.readValue(favoriteEventsJsonString, mapType)
    }
}