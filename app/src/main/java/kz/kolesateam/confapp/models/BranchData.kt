package kz.kolesateam.confapp.models

data class BranchData(
    val id: Int,
    val title: String,
    val events: List<EventData>
)