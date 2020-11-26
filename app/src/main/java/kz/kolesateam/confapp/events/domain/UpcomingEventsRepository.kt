package kz.kolesateam.confapp.events.domain

import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.utils.model.ResponseData

interface UpcomingEventsRepository {
    fun getUpcomingEvents() : ResponseData<List<UpcomingEventsListItem>, Exception>
}