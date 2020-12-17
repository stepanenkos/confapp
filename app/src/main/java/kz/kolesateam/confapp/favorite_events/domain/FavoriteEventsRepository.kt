package kz.kolesateam.confapp.favorite_events.domain

import kz.kolesateam.confapp.models.EventData

interface FavoriteEventsRepository {
    fun saveFavoriteEvent(
        eventData: EventData
    )

    fun removeFavoriteEvent(
        eventId: Int
    )

    fun getAllFavoriteEvents(): List<EventData>

    fun isFavorite(id: Int): Boolean
}