package kz.kolesateam.confapp.allevents.data

import kz.kolesateam.confapp.allevents.data.datasource.AllEventsDataSource
import kz.kolesateam.confapp.allevents.domain.AllEventsRepository
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.utils.mappers.EventApiDataMapper
import kz.kolesateam.confapp.utils.model.ResponseData

class DefaultAllEventsRepository(
    private val allEventsDataSource: AllEventsDataSource,
    private val eventApiDataMapper: EventApiDataMapper,
) : AllEventsRepository {

    override fun getAllEvents(
        branchId: Int,
    ): ResponseData<List<EventData>, Exception> {
        return try {
            val response = allEventsDataSource.getAllEvents(branchId).execute()

            if (response.isSuccessful) {
                val eventDataList: List<EventData> =
                    eventApiDataMapper.mapToListEventData(response.body()!!)

                ResponseData.Success(eventDataList)
            } else {
                ResponseData.Error(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            ResponseData.Error(e)
        }
    }
}
