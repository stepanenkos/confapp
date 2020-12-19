package kz.kolesateam.confapp.upcomingevents.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.presentation.AllEventsRouter
import kz.kolesateam.confapp.eventdetails.presentation.EventDetailsRouter
import kz.kolesateam.confapp.favoriteevents.domain.FavoriteEventActionObservable
import kz.kolesateam.confapp.favoriteevents.presentation.FavoriteEventsActivity
import kz.kolesateam.confapp.models.BranchData
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.presentation.listeners.UpcomingItemsClickListener
import kz.kolesateam.confapp.upcomingevents.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.upcomingevents.presentation.view.UpcomingEventsAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val BRANCH_ID = "branch_id"
const val BRANCH_TITLE = "branch_title"

class UpcomingEventsActivity : AppCompatActivity(), UpcomingItemsClickListener {

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()
    private val favoriteEventActionObservable: FavoriteEventActionObservable by inject()
    private val adapter = UpcomingEventsAdapter(
        upcomingItemsClickListener = this,
        favoriteEventActionObservable = favoriteEventActionObservable
    )

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
        progressState: ProgressState,
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
        startActivity(EventDetailsRouter().createIntentForEventDetails(this, eventData.id))
    }

    override fun onFavoritesClicked(eventData: EventData) {
        upcomingEventsViewModel.onFavoriteClick(eventData)
    }
}
