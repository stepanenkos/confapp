package kz.kolesateam.confapp.favoriteevents.domain

import kz.kolesateam.confapp.models.EventData

interface FavoritesRepository {
    fun saveFavoriteEvent(
        eventData: EventData
    )

    fun removeFavoriteEvent(
        eventId: Int
    )

    fun getAllFavoriteEvents(): List<EventData>

    fun isFavorite(id: Int): Boolean

    fun getFavoriteEvent(id: Int): EventData
}