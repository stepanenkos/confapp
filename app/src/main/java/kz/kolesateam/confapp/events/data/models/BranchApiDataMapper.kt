package kz.kolesateam.confapp.events.data.models

import kz.kolesateam.confapp.events.presentation.models.BranchData
import kz.kolesateam.confapp.events.presentation.models.EventData

private const val DEFAULT_BRANCH_NAME = "Название потока"

class BranchApiDataMapper : Mapper<BranchApiData, BranchData> {
    private val eventMapper: Mapper<List<EventApiData>, List<EventData>> = EventApiDataMapper()
    override fun map(data: BranchApiData?): BranchData {
        return BranchData(
            title = data?.title ?: DEFAULT_BRANCH_NAME,
            events = eventMapper.map(data?.events)
        )
    }
}