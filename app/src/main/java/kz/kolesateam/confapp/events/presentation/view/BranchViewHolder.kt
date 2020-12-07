package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.*
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.models.BranchData
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.models.SpeakerData
import kz.kolesateam.confapp.presentation.listeners.UpcomingItemsClickListener
import kz.kolesateam.confapp.presentation.view.BaseViewHolder
import java.text.SimpleDateFormat
import java.util.*

private const val FORMAT_STRING_FOR_DATE_AND_PLACE = "%s - %s • %s"

class BranchViewHolder(
    view: View,
    private val upcomingItemsClickListener: UpcomingItemsClickListener,
) : BaseViewHolder<UpcomingEventsListItem>(view) {

    private val horizontalScrollView: HorizontalScrollView =
        view.findViewById(R.id.activity_upcoming_events_horizontal_scroll_view)
    private val oldScrollX = horizontalScrollView.scrollX

    private val currentBranchEvent: View =
        view.findViewById(R.id.activity_upcoming_events_current_event)

    private val nextBranchEvent: View =
        view.findViewById(R.id.activity_upcoming_events_next_event)

    private val branchRow: View = view.findViewById(R.id.activity_upcoming_events_branch_row)

    private val branchNameTextView: TextView =
        view.findViewById(R.id.activity_upcoming_events_text_view_branch_name)

    private val currentDateAndPlaceTextView: TextView =
        currentBranchEvent.findViewById(R.id.events_card_layout_time_place_text_view)
    private val currentSpeakerFullNameTextView: TextView =
        currentBranchEvent.findViewById(R.id.events_card_layout_speaker_full_name_text_view)
    private val currentSpeakerJobTextView: TextView =
        currentBranchEvent.findViewById(R.id.events_card_layout_job_text_view)
    private val currentEventTitleTextView: TextView =
        currentBranchEvent.findViewById(R.id.events_card_layout_event_title_text_view)
    private val currentToFavoritesImageButton: ImageView =
        currentBranchEvent.findViewById(R.id.events_card_layout_to_favorites_image_view)

    private val nextDateAndPlaceTextView: TextView =
        nextBranchEvent.findViewById(R.id.events_card_layout_time_place_text_view)
    private val nextSpeakerFullNameTextView: TextView =
        nextBranchEvent.findViewById(R.id.events_card_layout_speaker_full_name_text_view)
    private val nextSpeakerJobTextView: TextView =
        nextBranchEvent.findViewById(R.id.events_card_layout_job_text_view)
    private val nextEventTitleTextView: TextView =
        nextBranchEvent.findViewById(R.id.events_card_layout_event_title_text_view)
    private val nextToFavoritesImageButton: ImageView =
        nextBranchEvent.findViewById(R.id.events_card_layout_to_favorites_image_view)

    private val currentBranchEventPaddingTop = currentBranchEvent.paddingTop
    private val currentBranchEventPaddingBottom = currentBranchEvent.paddingBottom
    private val currentBranchEventPaddingLeft = currentBranchEvent.paddingLeft
    private val currentBranchEventPaddingRight = currentBranchEvent.paddingRight

    private val nextBranchEventPaddingTop = nextBranchEvent.paddingTop
    private val nextBranchEventPaddingBottom = nextBranchEvent.paddingBottom
    private val nextBranchEventPaddingLeft = nextBranchEvent.paddingLeft
    private val nextBranchEventPaddingRight = nextBranchEvent.paddingRight

    init {
        currentBranchEvent.findViewById<TextView>(
            R.id.events_card_layout_next_event_text_view
        ).visibility = View.INVISIBLE


        nextBranchEvent.setBackgroundResource(R.drawable.bg_unfocused_event_card)
        nextBranchEvent.setPadding(
            nextBranchEventPaddingLeft,
            nextBranchEventPaddingTop,
            nextBranchEventPaddingRight,
            nextBranchEventPaddingBottom
        )
    }

    override fun onBind(data: UpcomingEventsListItem) {
        val branchData: BranchData =
            (data as? UpcomingEventsListItem.BranchListItem)?.data ?: return
        val currentEvent = branchData.events[0]
        val nextEvent = branchData.events[1]
        val branchId = branchData.id
        val currentSpeaker = currentEvent.speaker
        val nextSpeaker = nextEvent.speaker

        val currentDateAndPlaceString = formatStringForDateAndPlace(currentEvent)
        val nextDateAndPlaceString = formatStringForDateAndPlace(nextEvent)

        branchNameTextView.text = branchData.title

        fillCurrentEvent(currentDateAndPlaceString, currentSpeaker, currentEvent)
        fillNextEvent(nextDateAndPlaceString, nextSpeaker, nextEvent)

        setOnClickListeners(currentEvent, nextEvent, branchData)

    }

    private fun formatStringForDateAndPlace(event: EventData): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ROOT)
        val startTime = simpleDateFormat.format(event.startTime)
        val endTime = simpleDateFormat.format(event.endTime)
        return String.format(
            FORMAT_STRING_FOR_DATE_AND_PLACE,
            startTime,
            endTime,
            event.place
        )
    }

    private fun fillCurrentEvent(
        currentDateString: String,
        currentSpeaker: SpeakerData,
        currentEvent: EventData,
    ) {
        currentDateAndPlaceTextView.text = currentDateString
        currentSpeakerFullNameTextView.text = currentSpeaker.fullName
        currentSpeakerJobTextView.text = currentSpeaker.job
        currentEventTitleTextView.text = currentEvent.title
    }

    private fun fillNextEvent(
        nextDateString: String,
        nextSpeaker: SpeakerData,
        nextEvent: EventData,
    ) {
        nextDateAndPlaceTextView.text = nextDateString
        nextSpeakerFullNameTextView.text = nextSpeaker.fullName
        nextSpeakerJobTextView.text = nextSpeaker.job
        nextEventTitleTextView.text = nextEvent.title
    }

    private fun setOnClickListeners(
        currentEvent: EventData,
        nextEvent: EventData,
        branchData: BranchData,
    ) {
        branchRow.setOnClickListener {
            upcomingItemsClickListener.onBranchClick(
                branchData
            )
        }

        currentBranchEvent.setOnClickListener {
            upcomingItemsClickListener.onEventClick(
                currentEvent
            )
        }

        nextBranchEvent.setOnClickListener {
            upcomingItemsClickListener.onEventClick(
                nextEvent
            )
        }

        currentToFavoritesImageButton.setOnClickListener {
            currentEvent.isFavorite = !currentEvent.isFavorite
            val favoriteImageResource = getFavoriteImageResource(currentEvent.isFavorite)
            currentToFavoritesImageButton.setImageResource(favoriteImageResource)
            upcomingItemsClickListener.onFavoritesClicked(eventData = currentEvent)
        }

        nextToFavoritesImageButton.setOnClickListener {
            nextEvent.isFavorite = !nextEvent.isFavorite
            val favoriteImageResource = getFavoriteImageResource(nextEvent.isFavorite)
            nextToFavoritesImageButton.setImageResource(favoriteImageResource)
            upcomingItemsClickListener.onFavoritesClicked(eventData = nextEvent)
        }

        horizontalScrollView.viewTreeObserver.addOnScrollChangedListener {
            if (oldScrollX != horizontalScrollView.scrollX) {
                nextBranchEvent.setBackgroundResource(R.drawable.bg_focused_event_card)
                nextBranchEvent.setPadding(nextBranchEventPaddingLeft,
                    nextBranchEventPaddingTop,
                    nextBranchEventPaddingRight,
                    nextBranchEventPaddingBottom)
                currentBranchEvent.setBackgroundResource(R.drawable.bg_unfocused_event_card)
                currentBranchEvent.setPadding(currentBranchEventPaddingLeft,
                    currentBranchEventPaddingTop,
                    currentBranchEventPaddingRight,
                    currentBranchEventPaddingBottom)
            } else {
                nextBranchEvent.setBackgroundResource(R.drawable.bg_unfocused_event_card)
                nextBranchEvent.setPadding(nextBranchEventPaddingLeft,
                    nextBranchEventPaddingTop,
                    nextBranchEventPaddingRight,
                    nextBranchEventPaddingBottom)
                currentBranchEvent.setBackgroundResource(R.drawable.bg_focused_event_card)
                currentBranchEvent.setPadding(currentBranchEventPaddingLeft,
                    currentBranchEventPaddingTop,
                    currentBranchEventPaddingRight,
                    currentBranchEventPaddingBottom)
            }
        }
    }
    private fun getFavoriteImageResource(
        isFavorite: Boolean
    ): Int = when (isFavorite) {
        true -> R.drawable.ic_to_favorites_fill
        else -> R.drawable.ic_to_favorites_border
    }
}