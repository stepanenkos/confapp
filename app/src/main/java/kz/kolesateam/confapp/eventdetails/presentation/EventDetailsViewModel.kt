package kz.kolesateam.confapp.eventdetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.eventdetails.domain.EventDetailsRepository
import kz.kolesateam.confapp.favoriteevents.domain.FavoritesRepository
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper
import kz.kolesateam.confapp.utils.model.ResponseData

class EventDetailsViewModel(
    private val eventDetailsRepository: EventDetailsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val notificationAlarmHelper: NotificationAlarmHelper,
) : ViewModel() {
    private val eventDetailsLiveData: MutableLiveData<EventData> =
        MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getEventDetailsLiveData(): LiveData<EventData> = eventDetailsLiveData
    fun getErrorLiveData(): LiveData<Exception> = errorLiveData

    fun onStart(eventId: Int) {
        getEventDetails(eventId)
    }

    private fun getEventDetails(eventId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val eventDetailsResponse: ResponseData<EventData, Exception> =
                withContext(Dispatchers.IO) {
                    eventDetailsRepository.getEventDetails(eventId)
                }
            when (eventDetailsResponse) {
                is ResponseData.Success -> {
                    eventDetailsResponse.result.isFavorite = favoritesRepository.isFavorite(eventId)
                    eventDetailsLiveData.value = eventDetailsResponse.result
                }
                is ResponseData.Error -> {
                    errorLiveData.value = eventDetailsResponse.error
                }
            }
        }
    }

    fun onFavoriteClick(eventData: EventData) {
        when (eventData.isFavorite) {
            true -> {
                favoritesRepository.saveFavoriteEvent(eventData)
                scheduleEvent(eventData)
            }

            else -> {
                favoritesRepository.removeFavoriteEvent(eventData.id)
                cancelNotificationEvent(eventData)
            }
        }
    }

    private fun scheduleEvent(eventData: EventData) {
        notificationAlarmHelper.createNotificationAlarm(eventData)
    }

    private fun cancelNotificationEvent(eventData: EventData) {
        notificationAlarmHelper.cancelNotificationAlarm(eventData)
    }
}