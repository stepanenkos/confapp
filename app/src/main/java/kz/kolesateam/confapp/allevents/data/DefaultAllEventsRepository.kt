package kz.kolesateam.confapp.allevents.data

import kz.kolesateam.confapp.allevents.data.datasource.AllEventsDataSource
import kz.kolesateam.confapp.events.data.models.*
import kz.kolesateam.confapp.allevents.domain.AllEventsRepository
import kz.kolesateam.confapp.utils.model.ResponseData

class DefaultAllEventsRepository(
    private val allEventsDataSource: AllEventsDataSource,
) : AllEventsRepository {

    override fun getAllEvents(branchId: Int, branchTitle: String): ResponseData<List<UpcomingEventsListItem>, Exception> {
        try {
            val response = allEventsDataSource.getAllEvents(branchId).execute()

            if (response.isSuccessful) {
                val allEventListItemList: MutableList<UpcomingEventsListItem> =
                    mutableListOf()

                val branchTitleListItem: UpcomingEventsListItem =
                    UpcomingEventsListItem.BranchTitleItem(branchTitle)

                val eventListItemList: List<UpcomingEventsListItem> =
                    response.body()!!.map { eventApiData ->
                        EventApiDataMapper().map(eventApiData)
                    }.map {eventData ->
                        UpcomingEventsListItem.EventListItem(eventData)
                    }
                allEventListItemList.add(branchTitleListItem)
                allEventListItemList.addAll(eventListItemList)

                return ResponseData.Success(allEventListItemList)
            } else {
                return ResponseData.Error(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            return ResponseData.Error(e)
        }
    }
}
