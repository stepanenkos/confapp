package kz.kolesateam.confapp.upcomingevents.domain

import androidx.annotation.WorkerThread
import kz.kolesateam.confapp.models.BranchData
import kz.kolesateam.confapp.utils.model.ResponseData

interface UpcomingEventsRepository {
    @WorkerThread
    fun getUpcomingEvents() : ResponseData<List<BranchData>, Exception>
}