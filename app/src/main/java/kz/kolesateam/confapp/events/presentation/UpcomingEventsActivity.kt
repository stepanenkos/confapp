package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.SHARED_PREFS_DATA_SOURCE
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.view.EventClickListener
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventsAdapter
import kz.kolesateam.confapp.extensions.gone
import kz.kolesateam.confapp.extensions.show
import kz.kolesateam.confapp.utils.model.ResponseData
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import java.lang.Exception

private const val DEFAULT_USER_NAME = "Гость"

class UpcomingEventsActivity : AppCompatActivity(), EventClickListener {

    private val upcomingEventsRepository: UpcomingEventsRepository by inject()
    private val userNameDataSource: UserNameDataSource by inject(named(
        SHARED_PREFS_DATA_SOURCE))

    private val adapter = UpcomingEventsAdapter(this)

    private lateinit var upcomingEventsProgressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private lateinit var userName: String
    private var isPressedToFavoritesButton = true

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

        userName = userNameDataSource.getUserName() ?: DEFAULT_USER_NAME
    }

    override fun onFavoritesImageClick(view: View) {
        when (view.id) {
            R.id.activity_upcoming_events_image_to_favorites -> {
                view as ImageButton
                if (isPressedToFavoritesButton) {
                    view.setImageDrawable(ContextCompat.getDrawable(view.context,
                        R.drawable.ic_to_favorites_fill))
                } else {
                    view.setImageDrawable(ContextCompat.getDrawable(view.context,
                        R.drawable.ic_to_favorites_border))
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
        GlobalScope.launch(Dispatchers.IO) {
            val upcomingEventsResponse = upcomingEventsRepository.getUpcomingEvents()
            withContext(Dispatchers.Main) {
                when (upcomingEventsResponse) {
                    is ResponseData.Success -> showResult(upcomingEventsResponse.result)
                    is ResponseData.Error -> showError(upcomingEventsResponse.error)
                }
            }
        }
    }

    private fun showError(errorMessage: Exception) {
        upcomingEventsProgressBar.gone()
        Toast.makeText(this, errorMessage.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(listBranchApiData: List<UpcomingEventsListItem>) {
        upcomingEventsProgressBar.gone()
        adapter.setList(listBranchApiData)
    }

}
