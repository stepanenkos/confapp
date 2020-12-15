package kz.kolesateam.confapp.upcomingevents.domain

import kz.kolesateam.confapp.models.BranchData
import kz.kolesateam.confapp.utils.model.ResponseData

interface UpcomingEventsRepository {
    fun getUpcomingEvents() : ResponseData<List<BranchData>, Exception>
}