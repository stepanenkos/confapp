package kz.kolesateam.confapp.events.data.models

import kz.kolesateam.confapp.events.presentation.models.BranchData
import kz.kolesateam.confapp.events.presentation.models.EventData

const val HEADER_TYPE: Int = 0
const val BRANCH_TYPE: Int = 1
const val EVENT_TYPE: Int = 2
const val BRANCH_TITLE_TYPE: Int = 3

sealed class UpcomingEventsListItem(
    val type: Int,
) {
    data class HeaderItem(
        val userName: String,
    ) : UpcomingEventsListItem(HEADER_TYPE)

    data class BranchListItem(
        val data: BranchData,
    ) : UpcomingEventsListItem(BRANCH_TYPE)

    data class EventListItem(
        val data: EventData,
    ) : UpcomingEventsListItem(EVENT_TYPE)

    data class BranchTitleItem(
        val branchTitle: String,
    ) : UpcomingEventsListItem(BRANCH_TITLE_TYPE)
}
