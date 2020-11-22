package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData

class BranchViewHolder(
    view: View,
    private val eventClickListener: EventClickListener,
) : RecyclerView.ViewHolder(view) {
    private val currentBranchEvent: View =
        view.findViewById(R.id.activity_upcoming_events_current_event)
    private val nextBranchEvent: View =
        view.findViewById(R.id.activity_upcoming_events_next_event)
    private val branchRow: View = view.findViewById(R.id.activity_upcoming_events_branch_row)

    private val branchNameTextView: TextView =
        view.findViewById(R.id.activity_upcoming_events_text_view_branch_name)

    private val currentDateAndPlaceTextView: TextView =
        currentBranchEvent.findViewById(R.id.activity_upcoming_events_text_view_time_place)
    private val currentSpeakerFullNameTextView: TextView =
        currentBranchEvent.findViewById(R.id.activity_upcoming_events_text_view_speaker_full_name)
    private val currentSpeakerJobTextView: TextView =
        currentBranchEvent.findViewById(R.id.activity_upcoming_events_text_view_job)
    private val currentEventTitleTextView: TextView =
        currentBranchEvent.findViewById(R.id.activity_upcoming_events_text_view_event_title)
    private val currentToFavoritesImageButton: ImageView =
        currentBranchEvent.findViewById(R.id.activity_upcoming_events_image_to_favorites)

    private val nextDateAndPlaceTextView: TextView =
        nextBranchEvent.findViewById(R.id.activity_upcoming_events_text_view_time_place)
    private val nextSpeakerFullNameTextView: TextView =
        nextBranchEvent.findViewById(R.id.activity_upcoming_events_text_view_speaker_full_name)
    private val nextSpeakerJobTextView: TextView =
        nextBranchEvent.findViewById(R.id.activity_upcoming_events_text_view_job)
    private val nextEventTitleTextView: TextView =
        nextBranchEvent.findViewById(R.id.activity_upcoming_events_text_view_event_title)
    private val nextToFavoritesImageButton: ImageView =
        nextBranchEvent.findViewById(R.id.activity_upcoming_events_image_to_favorites)

    init {
        currentBranchEvent.findViewById<TextView>(
            R.id.activity_upcoming_events_text_view_next_event
        ).visibility = View.INVISIBLE

    }

    fun onBind(branchApiData: BranchApiData) {

        val currentEvent = branchApiData.events?.get(0)
        val nextEvent = branchApiData.events?.get(1)

        val currentSpeaker = currentEvent?.speaker
        val nextSpeaker = nextEvent?.speaker

        val currentDateAndPlace = String.format(
            "%s - %s • %s",
            currentEvent?.startTime?.substringBeforeLast(":"),
            currentEvent?.endTime?.substringBeforeLast(":"),
            currentEvent?.place
        )

        val nextDateAndPlace = String.format(
            "%s - %s • %s",
            nextEvent?.startTime?.substringBeforeLast(":"),
            nextEvent?.endTime?.substringBeforeLast(":"),
            nextEvent?.place
        )

        branchNameTextView.text = branchApiData.title

        currentDateAndPlaceTextView.text = currentDateAndPlace
        currentSpeakerFullNameTextView.text = currentSpeaker?.fullName
        currentSpeakerJobTextView.text = currentSpeaker?.job
        currentEventTitleTextView.text = currentEvent?.title

        nextDateAndPlaceTextView.text = nextDateAndPlace
        nextSpeakerFullNameTextView.text = nextSpeaker?.fullName
        nextSpeakerJobTextView.text = nextSpeaker?.job
        nextEventTitleTextView.text = nextEvent?.title

        branchRow.setOnClickListener {
            eventClickListener.onBranchClick(
                it,
                branchNameTextView.text.toString(),
            )
        }

        currentBranchEvent.setOnClickListener {
            eventClickListener.onEventClick(
                it,
                branchNameTextView.text.toString(),
                currentEvent?.title.toString()
            )
        }

        nextBranchEvent.setOnClickListener {
            eventClickListener.onEventClick(
                it,
                branchNameTextView.text.toString(),
                nextEvent?.title.toString()
            )
        }

        currentToFavoritesImageButton.setOnClickListener {
            eventClickListener.onFavoritesImageClick(it)
        }

        nextToFavoritesImageButton.setOnClickListener {
            eventClickListener.onFavoritesImageClick(it)
        }
    }
}