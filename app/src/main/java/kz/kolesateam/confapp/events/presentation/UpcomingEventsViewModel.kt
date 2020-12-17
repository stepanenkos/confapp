package kz.kolesateam.confapp.events.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.events.data.models.BRANCH_TYPE
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.models.BranchData
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.utils.model.ResponseData

class UpcomingEventsViewModel(
    private val upcomingEventsRepository: UpcomingEventsRepository,
    private val favoriteEventsRepository: FavoriteEventsRepository,
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
        when(eventData.isFavorite) {
            true -> favoriteEventsRepository.saveFavoriteEvent(eventData)
            else -> favoriteEventsRepository.removeFavoriteEvent(eventData.id)
        }
    }

    private fun getUpcomingEvents() {
        viewModelScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading

            val upcomingEventsResponse: ResponseData<List<UpcomingEventsListItem>, Exception> =
                withContext(Dispatchers.IO) {
                    upcomingEventsRepository.getUpcomingEvents()
                }

            when (upcomingEventsResponse) {
                is ResponseData.Success -> {
                    upcomingEventsResponse.result.forEach {
                        if(it.type == BRANCH_TYPE) {
                            it as UpcomingEventsListItem.BranchListItem
                            it.data.events.forEach { data ->
                                data.isFavorite = favoriteEventsRepository.isFavorite(data.id)
                            }
                        }
                    }
                    upcomingEventsLiveData.value =
                        upcomingEventsResponse.result
                }
                is ResponseData.Error -> errorLiveData.value = upcomingEventsResponse.error
            }

            progressLiveData.value = ProgressState.Done
        }
    }
}