package kz.kolesateam.confapp.events.data.models

import kz.kolesateam.confapp.events.presentation.models.BranchData

const val HEADER_TYPE: Int = 0
const val EVENT_TYPE: Int = 1

sealed class UpcomingEventsListItem(
    val type: Int,
) {
    data class HeaderItem(
        val userName: String,
    ) : UpcomingEventsListItem(HEADER_TYPE)

    data class BranchListItem(
        val data: BranchData,
    ) : UpcomingEventsListItem(EVENT_TYPE)
}
