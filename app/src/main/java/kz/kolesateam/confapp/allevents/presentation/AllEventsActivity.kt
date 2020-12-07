package kz.kolesateam.confapp.allevents.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.data.AllEventsListItem
import kz.kolesateam.confapp.allevents.presentation.view.AllEventsAdapter
import kz.kolesateam.confapp.events.presentation.BRANCH_ID
import kz.kolesateam.confapp.events.presentation.BRANCH_TITLE
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.presentation.listeners.AllEventsClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllEventsActivity() : AppCompatActivity(), AllEventsClickListener {
    private val allEventsViewModel: AllEventsViewModel by viewModel()
    private val adapter = AllEventsAdapter(this)

    private lateinit var allEventsProgressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonGoBack: ImageButton
    private lateinit var buttonToFavorites: Button

    private var branchId: Int = 0
    private var branchTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)
        initViews()
        observeAllEventsViewModel()
        allEventsViewModel.onStart(branchId, branchTitle)
    }

    private fun initViews() {
        branchId = intent.getIntExtra(BRANCH_ID, 0)
        branchTitle = intent.getStringExtra(BRANCH_TITLE) ?: ""

        allEventsProgressBar = findViewById(R.id.activity_all_events_progress_bar)
        buttonGoBack = findViewById(R.id.activity_all_events_button_go_back)
        buttonToFavorites = findViewById(R.id.button_to_favorites)

        recyclerView = findViewById(R.id.activity_all_events_recycler_view)
        recyclerView.apply {
            this.adapter = this@AllEventsActivity.adapter
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        buttonGoBack.setOnClickListener {
            finish()
        }

        buttonToFavorites.setOnClickListener{
            Toast.makeText(this, "Нажата кнопка ${"В избранные"} AllEventsActivity", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeAllEventsViewModel() {
        allEventsViewModel.getProgressLiveData().observe(this, ::handleProgressBarState)
        allEventsViewModel.getAllEventsLiveData().observe(this, ::showResult)
        allEventsViewModel.getErrorLiveData().observe(this, ::showError)
    }

    private fun handleProgressBarState(
        progressState: ProgressState
    ) {
        allEventsProgressBar.isVisible = progressState is ProgressState.Loading
    }

    private fun showError(errorMessage: Exception) {
        Toast.makeText(this, errorMessage.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(allEventsList: List<AllEventsListItem>) {
        adapter.setList(allEventsList)
    }

    override fun onEventClick(eventData: EventData) {
        Toast.makeText(this, "Event: ${eventData.title}", Toast.LENGTH_SHORT).show()

    }

    override fun onFavoritesClicked(eventData: EventData) {
        Toast.makeText(this, "Event: ${eventData.title}", Toast.LENGTH_SHORT).show()

    }
}