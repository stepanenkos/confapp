package kz.kolesateam.confapp.events.presentation.models

data class BranchData(
    val id: Int,
    val title: String,
    val events: List<EventData>
)
