package kz.kolesateam.confapp.events.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.presentation.AllEventsRouter
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.models.BranchData
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.events.presentation.view.EventsAdapter
import kz.kolesateam.confapp.favorite_events.presentation.FavoriteEventsActivity
import kz.kolesateam.confapp.presentation.listeners.UpcomingItemsClickListener
import kz.kolesateam.confapp.models.ProgressState
import org.koin.androidx.viewmodel.ext.android.viewModel

const val BRANCH_ID = "branch_id"
const val BRANCH_TITLE = "branch_title"

class UpcomingEventsActivity : AppCompatActivity(), UpcomingItemsClickListener {

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()
    private val adapter = EventsAdapter(this)

    private lateinit var upcomingEventsProgressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonToFavorites: Button

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
        }

        buttonToFavorites = findViewById(R.id.button_to_favorites)

        setOnClickListeners()
    }
    private fun setOnClickListeners() {
        buttonToFavorites.setOnClickListener {
            val intent = Intent(this, FavoriteEventsActivity::class.java)
            startActivity(intent)
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

    override fun onBranchClick(branchData: BranchData) {
        val intent = AllEventsRouter().createIntent(this@UpcomingEventsActivity)
        intent.putExtra(BRANCH_ID, branchData.id)
        intent.putExtra(BRANCH_TITLE, branchData.title)
        startActivity(intent)
    }

    override fun onEventClick(eventData: EventData) {
        Toast.makeText(this, "Event: ${eventData.title}", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onFavoritesClicked(eventData: EventData) {
        upcomingEventsViewModel.onFavoriteClick(eventData)

    }
}
