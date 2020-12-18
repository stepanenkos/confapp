package kz.kolesateam.confapp.favoriteevents.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.favoriteevents.domain.FavoritesRepository
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper

class FavoriteEventsViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val notificationAlarmHelper: NotificationAlarmHelper,
): ViewModel() {
    private val allFavoriteEventsLiveData: MutableLiveData<List<EventData>> =
        MutableLiveData()

    fun onStart() {
        getAllFavoriteEvents()
    }

    fun onFavoriteClick(eventData: EventData) {
        when(eventData.isFavorite) {
            true -> {
                favoritesRepository.saveFavoriteEvent(eventData)
                notificationAlarmHelper.createNotificationAlarm(eventData)
            }

            else -> {
                favoritesRepository.removeFavoriteEvent(eventData.id)
                notificationAlarmHelper.cancelNotificationAlarm(eventData)
            }
        }
    }

    fun getAllFavoriteEventsLiveData(): LiveData<List<EventData>> = allFavoriteEventsLiveData

    private fun getAllFavoriteEvents() {
        allFavoriteEventsLiveData.value = favoritesRepository.getAllFavoriteEvents()
    }
}