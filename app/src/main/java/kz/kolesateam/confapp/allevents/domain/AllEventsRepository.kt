package kz.kolesateam.confapp.allevents.domain

import kz.kolesateam.confapp.allevents.data.AllEventsListItem
import kz.kolesateam.confapp.utils.model.ResponseData

interface AllEventsRepository {
    fun getAllEvents(branchId: Int, branchTitle: String) : ResponseData<List<AllEventsListItem>, Exception>
}