package kz.kolesateam.confapp.events.data.models


import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.ApiClientSingleton
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
        fail: (String) -> Unit
    ) {
        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {
            override fun onResponse(
                call: Call<List<BranchApiData>>,
                response: Response<List<BranchApiData>>
            ) {
                if (response.isSuccessful) {
                    val upcomingEventListItemList: MutableList<UpcomingEventsListItem> =
                        mutableListOf()

                    val headerListItem: UpcomingEventsListItem = UpcomingEventsListItem(
                        type = 1,
                        data = userName
                    )
                    val list = response.body()!!.map {
                        branchApiDataMapper.map(it)
                    }
                    /*val branchListItemList: List<UpcomingEventsListItem> =
                        response.body()!!.map { branchApiData ->
                            UpcomingEventsListItem(
                                type = 2,
                                data = branchApiData
                            )
                        }*/

                    val branchListItemList: List<UpcomingEventsListItem> =
                        list.map { branchApiData ->
                            UpcomingEventsListItem(
                                type = 2,
                                data = branchApiData
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

