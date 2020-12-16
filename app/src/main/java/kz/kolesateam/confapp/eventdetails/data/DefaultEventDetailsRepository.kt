package kz.kolesateam.confapp.eventdetails.data

import kz.kolesateam.confapp.eventdetails.data.datasource.EventDetailsDataSource
import kz.kolesateam.confapp.eventdetails.domain.EventDetailsRepository
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.utils.mappers.EventApiDataMapper
import kz.kolesateam.confapp.utils.model.ResponseData

class DefaultEventDetailsRepository(
    private val eventDetailsDataSource: EventDetailsDataSource
) : EventDetailsRepository {
    override fun getEventDetails(eventId: Int): ResponseData<EventData, Exception> {
        return try {
            val response = eventDetailsDataSource.getEventDetails(eventId).execute()

            if(response.isSuccessful) {
                ResponseData.Success(EventApiDataMapper().map(response.body()!!))
            } else {
                ResponseData.Error(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            ResponseData.Error(e)
        }
    }

}