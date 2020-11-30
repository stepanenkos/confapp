package kz.kolesateam.confapp.events.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.domain.AllEventsRepository
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.utils.model.ResponseData

class AllEventsViewModel(
    private val allEventsRepository: AllEventsRepository,
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val allEventsLiveData: MutableLiveData<List<UpcomingEventsListItem>> =
        MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData

    fun getAllEventsLiveData(): LiveData<List<UpcomingEventsListItem>> = allEventsLiveData

    fun getErrorLiveData(): LiveData<Exception> = errorLiveData

    fun onStart(branchId: Int, branchTitle: String) {
        getAllEvents(branchId, branchTitle)
    }

    private fun getAllEvents(branchId: Int, branchTitle: String) {
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading

            val allEventsResponse: ResponseData<List<UpcomingEventsListItem>, Exception> =
                withContext(Dispatchers.IO) {
                    allEventsRepository.getAllEvents(branchId, branchTitle)
                }

            when (allEventsResponse) {
                is ResponseData.Success -> allEventsLiveData.value =
                    allEventsResponse.result
                is ResponseData.Error -> errorLiveData.value = allEventsResponse.error
            }

            progressLiveData.value = ProgressState.Done
        }
    }

}