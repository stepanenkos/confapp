package kz.kolesateam.confapp.upcomingevents.data.datasource

import kz.kolesateam.confapp.upcomingevents.data.models.BranchApiData
import retrofit2.Call
import retrofit2.http.GET

interface UpcomingEventsDataSource {
    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>
}