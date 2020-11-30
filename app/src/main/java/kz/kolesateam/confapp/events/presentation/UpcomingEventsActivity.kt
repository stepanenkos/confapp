package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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

class UpcomingEventsActivity : AppCompatActivity(), EventClickListener {

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()

    private val adapter = UpcomingEventsAdapter(this)

    private lateinit var upcomingEventsProgressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private lateinit var buttonToFavorites: Button

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
        recyclerView.apply {
            this.adapter = this@UpcomingEventsActivity.adapter
            this.layoutManager = LinearLayoutManager(this@UpcomingEventsActivity)
        }

        buttonToFavorites = findViewById(R.id.button_to_favorites)

        buttonToFavorites.setOnClickListener {
            Toast.makeText(this, "Нажата кнопка ${"В избранные"}", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onBranchClick(view: View, branchTitle: String) {
        when (view.id) {
            R.id.activity_upcoming_events_branch_row ->
                Toast.makeText(this, "Branch: $branchTitle", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onEventClick(view: View, eventTitle: String) {
        when (view.id) {
            R.id.activity_upcoming_events_current_event ->
                Toast.makeText(this, "Event: $eventTitle", Toast.LENGTH_SHORT)
                    .show()

            R.id.activity_upcoming_events_next_event ->
                Toast.makeText(this, "Event: $eventTitle", Toast.LENGTH_SHORT)
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
