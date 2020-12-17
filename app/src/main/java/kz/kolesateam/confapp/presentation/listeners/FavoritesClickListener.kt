package kz.kolesateam.confapp.presentation.listeners

import kz.kolesateam.confapp.models.EventData

interface FavoritesClickListener {
    fun onFavoritesClicked(eventData: EventData)
}