package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.presentation.view.EventClickListener
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventsAdapter
import kz.kolesateam.confapp.models.ProgressState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

private const val DEFAULT_USER_NAME = "Гость"

class UpcomingEventsActivity : AppCompatActivity(), EventClickListener {

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()

    private val adapter = UpcomingEventsAdapter(this)

    private lateinit var upcomingEventsProgressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private var isPressedToFavoritesButton = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        initViews()
        observeUpcomingEventsViewModel()
        upcomingEventsViewModel.onStart()
    }

    private fun observeUpcomingEventsViewModel() {
        upcomingEventsViewModel.getProgressLiveData().observe(this, ::handleProgressBarState)
        upcomingEventsViewModel.getUpcomingEventsLiveData().observe(this, ::showResult)
        upcomingEventsViewModel.getErrorLiveData().observe(this, ::showError)
    }

    private fun initViews() {
        upcomingEventsProgressBar = findViewById(R.id.activity_upcoming_events_progress_bar)

        recyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            this
        )
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

    override fun onBranchClick(view: View, branchTitle: String) {
        when (view.id) {
            R.id.activity_upcoming_events_branch_row ->
                Toast.makeText(this, "Branch: $branchTitle", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onEventClick(view: View, branchTitle: String, eventTitle: String) {
        when (view.id) {
            R.id.activity_upcoming_events_current_event ->
                Toast.makeText(this, "Branch: $branchTitle, Event: $eventTitle", Toast.LENGTH_SHORT)
                    .show()

            R.id.activity_upcoming_events_next_event ->
                Toast.makeText(this, "Branch: $branchTitle, Event: $eventTitle", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    private fun handleProgressBarState(
        progressState: ProgressState
    ) {
        upcomingEventsProgressBar.isVisible = progressState is ProgressState.Loading
    }

    private fun showError(errorMessage: Exception) {
        Toast.makeText(this, errorMessage.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(upcomingEventsList: List<UpcomingEventsListItem>) {
        adapter.setList(upcomingEventsList)
    }
}
