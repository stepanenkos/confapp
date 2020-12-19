package kz.kolesateam.confapp.favoriteevents.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.eventdetails.presentation.EventDetailsRouter
import kz.kolesateam.confapp.favoriteevents.domain.FavoriteEventActionObservable
import kz.kolesateam.confapp.upcomingevents.presentation.UpcomingEventsRouter
import kz.kolesateam.confapp.favoriteevents.presentation.view.FavoriteEventsAdapter
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.presentation.listeners.AllEventsClickListener
import org.koin.android.ext.android.inject

class FavoriteEventsActivity : AppCompatActivity(), AllEventsClickListener {
    private val favoriteEventsViewModel: FavoriteEventsViewModel by inject()
    private val favoriteEventActionObservable: FavoriteEventActionObservable by inject()
    private lateinit var recyclerView: RecyclerView
    private lateinit var toHomeButton: Button
    private lateinit var containerForEmptyActivity: View
    private val adapter = FavoriteEventsAdapter(
        allEventsClickListener = this,
        favoriteEventActionObservable = favoriteEventActionObservable
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_events)
        initViews()
        observeFavoriteEventsViewModel()
        favoriteEventsViewModel.onStart()
    }

    override fun onStart() {
        super.onStart()
        favoriteEventsViewModel.onStart()
    }
    private fun initViews() {
        recyclerView = findViewById(R.id.activity_favorite_events_recycler_view)
        recyclerView.apply {
            this.adapter = this@FavoriteEventsActivity.adapter
        }
        toHomeButton = findViewById(R.id.activity_favorite_events_button_to_home)
        containerForEmptyActivity = findViewById(R.id.activity_favorite_events_content_for_empty_activity)
        setOnClickListeners()
    }

    private fun observeFavoriteEventsViewModel() {
        favoriteEventsViewModel.getAllFavoriteEventsLiveData().observe(this, ::showResult)
    }

    private fun showResult(allEventsList: List<EventData>) {
        if(allEventsList.isNotEmpty()) {
            containerForEmptyActivity.visibility = View.INVISIBLE
        } else {
            containerForEmptyActivity.visibility = View.VISIBLE
        }
        adapter.setList(allEventsList)
    }

    private fun setOnClickListeners() {
        toHomeButton.setOnClickListener{
            startActivity(UpcomingEventsRouter().createIntent(this))
            finish()
        }
    }

    override fun onEventClick(eventData: EventData) {
        startActivity(EventDetailsRouter().createIntentForEventDetails(this, eventData.id))
    }

    override fun onFavoritesClicked(eventData: EventData) {
        favoriteEventsViewModel.onFavoriteClick(eventData)
    }
}