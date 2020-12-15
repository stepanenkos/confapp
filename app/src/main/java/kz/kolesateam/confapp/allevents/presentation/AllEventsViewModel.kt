package kz.kolesateam.confapp.allevents.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.allevents.data.AllEventsListItem
import kz.kolesateam.confapp.allevents.data.EVENT_TYPE
import kz.kolesateam.confapp.allevents.domain.AllEventsRepository
import kz.kolesateam.confapp.favorite_events.domain.FavoritesRepository
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper
import kz.kolesateam.confapp.utils.model.ResponseData

class AllEventsViewModel(
    private val allEventsRepository: AllEventsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val notificationAlarmHelper: NotificationAlarmHelper,
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val allEventsLiveData: MutableLiveData<List<AllEventsListItem>> =
        MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData

    fun getAllEventsLiveData(): LiveData<List<AllEventsListItem>> = allEventsLiveData

    fun getErrorLiveData(): LiveData<Exception> = errorLiveData

    fun onStart(branchId: Int, branchTitle: String) {
        getAllEvents(branchId, branchTitle)
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

    private fun getAllEvents(branchId: Int, branchTitle: String) {
        viewModelScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading

            val allEventsResponse: ResponseData<List<AllEventsListItem>, Exception> =
                withContext(Dispatchers.IO) {
                    allEventsRepository.getAllEvents(branchId, branchTitle)
                }

            when (allEventsResponse) {
                is ResponseData.Success -> {
                    allEventsResponse.result.forEach { allEventsListItem ->
                        if (allEventsListItem.type == EVENT_TYPE) {
                            val eventData = allEventsListItem as AllEventsListItem.EventListItem
                            eventData.data.isCompleted = isCompleted(eventData.data)
                        }
                    }
                    allEventsLiveData.value =
                        allEventsResponse.result
                }
                is ResponseData.Error -> errorLiveData.value = allEventsResponse.error
            }

            progressLiveData.value = ProgressState.Done
        }
    }

    private fun isCompleted(eventData: EventData): Boolean {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ROOT)
        val dateNowFormat = simpleDateFormat.format(Date())
        val dateNow = simpleDateFormat.parse(dateNowFormat)!!
        return dateNow.after(eventData.endTime)
    }
}