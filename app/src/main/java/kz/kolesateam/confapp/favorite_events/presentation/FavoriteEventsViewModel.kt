package kz.kolesateam.confapp.favorite_events.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.models.EventData

class FavoriteEventsViewModel(
    private val favoriteEventsRepository: FavoriteEventsRepository,
): ViewModel() {
    private val allFavoriteEventsLiveData: MutableLiveData<List<EventData>> =
        MutableLiveData()

    fun onStart() {
        getAllFavoriteEvents()
    }

    fun onFavoriteClick(eventData: EventData) {
        when(eventData.isFavorite) {
            true -> favoriteEventsRepository.saveFavoriteEvent(eventData)
            else -> favoriteEventsRepository.removeFavoriteEvent(eventData.id)
        }
    }

    fun getAllFavoriteEventsLiveData(): LiveData<List<EventData>> = allFavoriteEventsLiveData

    private fun getAllFavoriteEvents() {
        allFavoriteEventsLiveData.value = favoriteEventsRepository.getAllFavoriteEvents()
    }
}