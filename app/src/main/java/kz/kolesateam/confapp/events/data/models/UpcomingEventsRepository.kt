package kz.kolesateam.confapp.events.data.models


import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.ApiClientSingleton
import kz.kolesateam.confapp.events.presentation.models.BranchData
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
    private val branchApiDataMapper: Mapper<BranchApiData, BranchData> = BranchApiDataMapper()
    fun getUpcomingEvents(
        userName: String,
        result: (List<UpcomingEventsListItem>) -> Unit,
        fail: (String) -> Unit,
    ) {
        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {
            override fun onResponse(
                call: Call<List<BranchApiData>>,
                response: Response<List<BranchApiData>>,
            ) {
                if (response.isSuccessful) {
                    val upcomingEventListItemList: MutableList<UpcomingEventsListItem> =
                        mutableListOf()

                    val headerListItem: UpcomingEventsListItem = UpcomingEventsListItem(
                        type = 1,
                        data = userName
                    )

                    val branchListItemList: List<UpcomingEventsListItem> =
                        response.body()!!.map { branchApiData ->
                            branchApiDataMapper.map(branchApiData)
                        }.map { branchData ->
                            UpcomingEventsListItem(
                                type = 2,
                                data = branchData
                            )
                        }
                    
                    upcomingEventListItemList.add(headerListItem)
                    upcomingEventListItemList.addAll(branchListItemList)

                    result(upcomingEventListItemList)
                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                fail(t.localizedMessage!!)
            }

        })
    }
}

