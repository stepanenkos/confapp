package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.BranchApiDataMapper
import kz.kolesateam.confapp.events.data.models.Mapper
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.models.BranchData
import kz.kolesateam.confapp.utils.model.ResponseData

class DefaultUpcomingEventsRepository(
    private val upcomingEventsDataSource: UpcomingEventsDataSource,
    private val userNameDataSource: UserNameDataSource,
) : UpcomingEventsRepository {
    private val branchApiDataMapper: Mapper<BranchApiData, BranchData> = BranchApiDataMapper()

    override fun getUpcomingEvents(): ResponseData<List<UpcomingEventsListItem>, Exception> {
        try {
            val response = upcomingEventsDataSource.getUpcomingEvents().execute()
            val userName = userNameDataSource.getUserName()

            if (response.isSuccessful) {
                val upcomingEventListItem: MutableList<UpcomingEventsListItem> =
                    mutableListOf()

                val headerListItem: UpcomingEventsListItem =
                    UpcomingEventsListItem.HeaderItem(userName)

                val branchListItemList: List<UpcomingEventsListItem> =
                    response.body()!!.map { branchApiData ->
                        UpcomingEventsListItem.BranchListItem(branchApiDataMapper.map(branchApiData))
                    }

                upcomingEventListItem.add(headerListItem)
                upcomingEventListItem.addAll(branchListItemList)

                return ResponseData.Success(upcomingEventListItem)
            } else {
                return ResponseData.Error(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            return ResponseData.Error(e)
        }
    }
}
