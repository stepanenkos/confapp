package kz.kolesateam.confapp.utils.mappers

import kz.kolesateam.confapp.upcomingevents.data.models.BranchApiData
import kz.kolesateam.confapp.models.BranchData

private const val DEFAULT_BRANCH_NAME = ""

class BranchApiDataMapper : Mapper<List<BranchApiData>?, List<BranchData>> {
    private val eventMapper = EventApiDataMapper()

    override fun map(data: List<BranchApiData>?): List<BranchData> {
        data ?: return emptyList()

        return data.map {
            BranchData(
                id = it.id ?: 0,
                title = it.title ?: DEFAULT_BRANCH_NAME,
                events = eventMapper.map(it.events)
            )
        }
    }
}