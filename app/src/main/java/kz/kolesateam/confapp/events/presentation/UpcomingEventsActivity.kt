package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.ApiClientSingleton
import kz.kolesateam.confapp.events.data.JetpackDataStore
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.data.models.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.view.EventClickListener
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventsAdapter
import kz.kolesateam.confapp.extensions.gone
import kz.kolesateam.confapp.extensions.show
import kz.kolesateam.confapp.hello.presentation.SHARED_PREFERENCES_NAME_KEY
import kz.kolesateam.confapp.hello.presentation.USER_NAME
import retrofit2.converter.jackson.JacksonConverterFactory

private const val DEFAULT_USER_NAME = "Гость"

class UpcomingEventsActivity : AppCompatActivity(), EventClickListener {

    private lateinit var upcomingEventsProgressBar: ProgressBar

    private lateinit var recyclerView: RecyclerView
    private val adapter = UpcomingEventsAdapter(this)

    private val upcomingEventsRepository = UpcomingEventsRepository()

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userName: String
    private var isPressedToFavoritesButton = true
    private val apiClient: ApiClient = ApiClientSingleton.getApiClient(
        "http://37.143.8.68:2020",
        JacksonConverterFactory.create(),
        ApiClient::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        initViews()
        getUpcomingEvents()
    }

    private fun initViews() {
        upcomingEventsProgressBar = findViewById(R.id.activity_upcoming_events_progress_bar)

        recyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            this
        )

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME_KEY, Context.MODE_PRIVATE)
        userName = sharedPreferences.getString(USER_NAME, DEFAULT_USER_NAME)!!
    }

    override fun onFavoritesImageClick(view: View) {
        when (view.id) {
            R.id.activity_upcoming_events_image_to_favorites -> {
                view as ImageButton
                if (isPressedToFavoritesButton) {
                    view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_to_favorites_fill))
                } else {
                    view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_to_favorites_border))
                }
                isPressedToFavoritesButton = !isPressedToFavoritesButton
            }
        }
    }

    override fun onBranchClick(view: View, branchTitle: String?) {
        when (view.id) {
            R.id.activity_upcoming_events_branch_row ->
                Toast.makeText(this, "Branch: $branchTitle", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onEventClick(view: View, branchTitle: String?, eventTitle: String?) {
        when (view.id) {
            R.id.activity_upcoming_events_current_event ->
                Toast.makeText(this, "Branch: $branchTitle, Event: $eventTitle", Toast.LENGTH_SHORT)
                    .show()

            R.id.activity_upcoming_events_next_event ->
                Toast.makeText(this, "Branch: $branchTitle, Event: $eventTitle", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    private fun getUpcomingEvents() {
        upcomingEventsProgressBar.show()
        upcomingEventsRepository.getUpcomingEvents(
            getString(
                R.string.activity_upcoming_events_text_view_hello_user,
                userName
            ),
            result = { listBranchApiData ->
                showResult(listBranchApiData)
            },
            fail = { errorMessage ->
                showError(errorMessage)
            })
        upcomingEventsProgressBar.gone()
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(listBranchApiData: List<UpcomingEventsListItem>) {
        adapter.setList(listBranchApiData)
    }

}
