package kz.kolesateam.confapp.events.data.models


import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.ApiClientSingleton
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.jackson.JacksonConverterFactory

class UpcomingEventsRepository {

    private val apiClient: ApiClient = ApiClientSingleton.getApiClient(
        "http://37.143.8.68:2020",
        JacksonConverterFactory.create(),
        ApiClient::class.java
    )

    fun getUpcomingEventsAsync(
        result: (List<BranchApiData>) -> Unit,
        fail: (String) -> Unit
    ) {
        apiClient.getUpcomingEventsAsync().enqueue(object: Callback<List<BranchApiData>> {
            override fun onResponse(call: Call<List<BranchApiData>>, response: Response<List<BranchApiData>>) {
                if(response.isSuccessful) {
                    result(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                fail(t.localizedMessage!!)
            }

        })
    }

    fun loadUpcomingEventsSync(): List<BranchApiData> {

        val branchApiDataList: MutableList<BranchApiData> = mutableListOf()
        val response = apiClient.getUpcomingEventsSync().execute().body()!!
        val textJsonBody = response.string()
        val responseJsonArray = JSONArray(textJsonBody)

        branchApiDataList.addAll(parseBranchesJsonArray(responseJsonArray))

        return branchApiDataList
    }

    private fun parseBranchesJsonArray(
        responseJsonArray: JSONArray
    ): List<BranchApiData> {
        val branchList = mutableListOf<BranchApiData>()
        for (index in 0 until responseJsonArray.length()) {
            val branchJsonObject = (responseJsonArray[index] as? JSONObject) ?: continue
            val branchApiData = parseBranchJsonObject(branchJsonObject)
            branchList.add(branchApiData)
        }
        return branchList
    }

    private fun parseBranchJsonObject(
        branchJsonObject: JSONObject
    ): BranchApiData {
        val id = branchJsonObject.getInt("id")
        val title = branchJsonObject.getString("title")
        val eventsJsonArray = branchJsonObject.getJSONArray("events")

        val eventsApiList = mutableListOf<EventApiData>()

        for (index in 0 until eventsJsonArray.length()) {
            val eventsJsonObject = (eventsJsonArray[index] as? JSONObject) ?: continue
            val eventApiData = parseEventJsonObject(eventsJsonObject)
            eventsApiList.add(eventApiData)
        }

        return BranchApiData(
            id = id,
            title = title,
            events = eventsApiList
        )
    }

    private fun parseEventJsonObject(eventJsonObject: JSONObject): EventApiData {
        val id = eventJsonObject.getInt("id")
        val startTime = eventJsonObject.getString("startTime")
        val endTime = eventJsonObject.getString("endTime")
        val title = eventJsonObject.getString("title")
        val description = eventJsonObject.getString("description")
        val place = eventJsonObject.getString("place")
        val speakerJsonObject = (eventJsonObject.get("speaker") as? JSONObject)

        val speakerApiData: SpeakerApiData? = speakerJsonObject?.let {
            parseSpeakerJsonObject(it)
        }

        return EventApiData(
            id = id,
            startTime = startTime,
            endTime = endTime,
            title = title,
            description = description,
            place = place,
            speaker = speakerApiData
        )
    }

    private fun parseSpeakerJsonObject(speakerJsonObject: JSONObject): SpeakerApiData {
        val id = speakerJsonObject.getInt("id")
        val fullName = speakerJsonObject.getString("fullName")
        val job = speakerJsonObject.getString("job")
        val photoUrl = speakerJsonObject.getString("photoUrl")

        return SpeakerApiData(
            id = id,
            fullName = fullName,
            job = job,
            photoUrl = photoUrl
        )
    }
}

