package kz.kolesateam.confapp.utils.mappers

import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.models.BranchData
import kz.kolesateam.confapp.models.EventData

private const val DEFAULT_BRANCH_NAME = "Название потока"

class BranchApiDataMapper : Mapper<BranchApiData, BranchData> {
    private val eventMapper: Mapper<List<EventApiData>, List<EventData>> = EventApiDataMapper()
    override fun map(data: BranchApiData?): BranchData {
        return BranchData(
            id = data?.id ?: 0,
            title = data?.title ?: DEFAULT_BRANCH_NAME,
            events = eventMapper.map(data?.events)
        )
    }

     fun map(data: List<BranchApiData>?): List<BranchData> {
        val listBranchData: MutableList<BranchData> = mutableListOf()

        for (index in data?.indices!!) {
            listBranchData.add(
                BranchData(
                    id = data[index].id ?: 0,
                    title = data[index].title ?: DEFAULT_BRANCH_NAME,
                    events = eventMapper.map(data?.get(index)?.events)
                )
            )
        }
        return listBranchData
    }
}