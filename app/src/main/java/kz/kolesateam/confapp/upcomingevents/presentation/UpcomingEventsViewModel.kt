package kz.kolesateam.confapp.upcomingevents.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.upcomingevents.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.upcomingevents.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.favoriteevents.domain.FavoritesRepository
import kz.kolesateam.confapp.models.BranchData
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper
import kz.kolesateam.confapp.user.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.utils.model.ResponseData

class UpcomingEventsViewModel(
    private val upcomingEventsRepository: UpcomingEventsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val notificationAlarmHelper: NotificationAlarmHelper,
    private val userNameDataSource: UserNameDataSource,
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val upcomingEventsLiveData: MutableLiveData<List<UpcomingEventsListItem>> =
        MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData

    fun getUpcomingEventsLiveData(): LiveData<List<UpcomingEventsListItem>> = upcomingEventsLiveData

    fun getErrorLiveData(): LiveData<Exception> = errorLiveData

    fun onStart() {
        getUpcomingEvents()
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

    private fun getUpcomingEvents() {
        viewModelScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading

            val upcomingEventsResponse: ResponseData<List<BranchData>, Exception> =
                withContext(Dispatchers.IO) {
                    upcomingEventsRepository.getUpcomingEvents()
                }

            when (upcomingEventsResponse) {
                is ResponseData.Success -> {
                    val userName = userNameDataSource.getUserName()
                    val upcomingEventListItem: MutableList<UpcomingEventsListItem> =
                        mutableListOf()

                    val headerListItem: UpcomingEventsListItem =
                        UpcomingEventsListItem.HeaderItem(userName)

                    upcomingEventListItem.add(headerListItem)

                    val branchDataList = upcomingEventsResponse.result

                    branchDataList.forEach {
                        it.events.forEach { eventData ->
                            eventData.isFavorite = favoritesRepository.isFavorite(eventData.id)
                        }
                    }

                    for (index in branchDataList.indices) {
                        upcomingEventListItem.add(UpcomingEventsListItem.BranchListItem(
                            branchDataList[index]))
                    }

                    upcomingEventsLiveData.value =
                        upcomingEventListItem
                }
                is ResponseData.Error -> errorLiveData.value = upcomingEventsResponse.error
            }

            progressLiveData.value = ProgressState.Done
        }
    }
}