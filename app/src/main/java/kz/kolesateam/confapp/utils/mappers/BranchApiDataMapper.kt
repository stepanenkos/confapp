package kz.kolesateam.confapp.utils.mappers

import kz.kolesateam.confapp.upcomingevents.data.models.BranchApiData
import kz.kolesateam.confapp.models.BranchData

private const val DEFAULT_BRANCH_NAME = ""
private const val DEFAULT_BRANCH_ID = 0

class BranchApiDataMapper {
    private val eventMapper = EventApiDataMapper()

    fun map(data: List<BranchApiData>?): List<BranchData> {
        data ?: return emptyList()

        return data.map {
            BranchData(
                id = it.id ?: DEFAULT_BRANCH_ID,
                title = it.title ?: DEFAULT_BRANCH_NAME,
                events = eventMapper.mapToListEventData(it.events)
            )
        }
    }
}