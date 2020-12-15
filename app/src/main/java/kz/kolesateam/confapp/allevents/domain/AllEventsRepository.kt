package kz.kolesateam.confapp.allevents.domain

import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.utils.model.ResponseData

interface AllEventsRepository {
    fun getAllEvents(branchId: Int) : ResponseData<List<EventData>, Exception>
}