package kz.kolesateam.confapp.upcomingevents.data

import kz.kolesateam.confapp.upcomingevents.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.upcomingevents.data.models.BranchApiData
import kz.kolesateam.confapp.utils.mappers.BranchApiDataMapper
import kz.kolesateam.confapp.utils.mappers.Mapper
import kz.kolesateam.confapp.upcomingevents.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.models.BranchData
import kz.kolesateam.confapp.utils.model.ResponseData

class DefaultUpcomingEventsRepository(
    private val upcomingEventsDataSource: UpcomingEventsDataSource,
) : UpcomingEventsRepository {
    private val branchApiDataMapper: Mapper<BranchApiData, BranchData> = BranchApiDataMapper()

    override fun getUpcomingEvents(): ResponseData<List<BranchData>, Exception> {
        return try {
            val response = upcomingEventsDataSource.getUpcomingEvents().execute()

            if (response.isSuccessful) {

                val branchDataList: List<BranchData> =
                    response.body()!!.map { branchApiData ->
                        branchApiDataMapper.map(branchApiData)
                    }

                ResponseData.Success(branchDataList)
            } else {
                ResponseData.Error(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            return ResponseData.Error(e)
        }
    }
}
