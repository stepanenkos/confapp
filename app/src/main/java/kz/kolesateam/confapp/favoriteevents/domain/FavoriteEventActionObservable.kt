package kz.kolesateam.confapp.favoriteevents.domain

import java.util.*
import kz.kolesateam.confapp.favoriteevents.domain.model.FavoriteActionEvent

class FavoriteEventActionObservable : Observable() {
    fun subscribe(favoritesObserver: Observer) {
        addObserver(favoritesObserver)
    }

    fun unsubscribe(favoritesObserver: Observer) {
        deleteObserver(favoritesObserver)
    }

    fun notifyChanged(eventId: Int, isFavorite: Boolean) {
        notify(
            FavoriteActionEvent(
                eventId = eventId,
                isFavorite = isFavorite,
            )
        )
    }

    private fun notify(favoriteActionEvent: FavoriteActionEvent) {
        setChanged()
        notifyObservers(favoriteActionEvent)
    }
}