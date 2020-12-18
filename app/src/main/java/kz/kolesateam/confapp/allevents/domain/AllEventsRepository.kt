package kz.kolesateam.confapp.allevents.domain

import androidx.annotation.WorkerThread
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.utils.model.ResponseData

interface AllEventsRepository {
    @WorkerThread
    fun getAllEvents(branchId: Int) : ResponseData<List<EventData>, Exception>
}