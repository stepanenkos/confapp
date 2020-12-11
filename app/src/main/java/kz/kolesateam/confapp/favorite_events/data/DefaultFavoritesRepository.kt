package kz.kolesateam.confapp.favorite_events.data

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.MapType
import kz.kolesateam.confapp.favorite_events.domain.FavoritesRepository
import kz.kolesateam.confapp.models.EventData
import java.io.FileInputStream
import java.io.FileOutputStream

private const val FAVORITE_EVENTS_FILE_NAME = "favorite_events.json"

class DefaultFavoritesRepository(
    private val context: Context,
    private val objectMapper: ObjectMapper,
) : FavoritesRepository {
    private var favoriteEvents: MutableMap<Int, EventData> = mutableMapOf()

    init {
        val favoriteEventsFromFile = getFavoriteEventsFromFile()
        favoriteEvents.putAll(favoriteEventsFromFile)
    }

    override fun saveFavoriteEvent(eventData: EventData) {
        favoriteEvents[eventData.id] = eventData
        saveFavoriteEventsToFile()
    }

    override fun removeFavoriteEvent(eventId: Int) {
        favoriteEvents.remove(eventId)
        saveFavoriteEventsToFile()
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
        val mapType: MapType = objectMapper.typeFactory.constructMapType(
            Map::class.java,
            Int::class.java,
            EventData::class.java
        )
        return objectMapper.readValue(favoriteEventsJsonString, mapType)
    }
}