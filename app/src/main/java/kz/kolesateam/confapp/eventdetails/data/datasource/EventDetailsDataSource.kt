package kz.kolesateam.confapp.eventdetails.data.datasource

import kz.kolesateam.confapp.upcomingevents.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventDetailsDataSource {

    @GET("/events/{event_id}")
    fun getEventDetails(@Path("event_id") eventId: Int): Call<EventApiData>
}