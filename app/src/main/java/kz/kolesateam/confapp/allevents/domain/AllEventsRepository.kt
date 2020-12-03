package kz.kolesateam.confapp.allevents.domain

import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.utils.model.ResponseData

interface AllEventsRepository {
    fun getAllEvents(branchId: Int, branchTitle: String) : ResponseData<List<UpcomingEventsListItem>, Exception>
}