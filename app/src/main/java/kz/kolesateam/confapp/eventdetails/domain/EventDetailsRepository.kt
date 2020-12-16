package kz.kolesateam.confapp.eventdetails.domain

import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.utils.model.ResponseData

interface EventDetailsRepository {
    fun getEventDetails(eventId: Int): ResponseData<EventData, Exception>
}