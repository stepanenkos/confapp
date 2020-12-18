package kz.kolesateam.confapp.allevents.presentation.view

import android.view.View
import android.widget.*
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.data.AllEventsListItem
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.presentation.listeners.AllEventsClickListener
import kz.kolesateam.confapp.utils.extensions.zoned_date_time.getEventFormattedDateTime
import java.util.*
import kz.kolesateam.confapp.favoriteevents.domain.FavoriteEventActionObservable
import kz.kolesateam.confapp.favoriteevents.domain.model.FavoriteActionEvent

private const val FORMAT_STRING_FOR_DATE_AND_PLACE = "%s - %s • %s"
private const val DATE_TIME_FORMAT = "HH:mm"

class EventsViewHolder(
    view: View,
    private val allEventsClickListener: AllEventsClickListener,
    private val favoriteEventActionObservable: FavoriteEventActionObservable,
) : BaseViewHolder<AllEventsListItem>(view) {
    private val favoriteObserver: Observer = object : Observer {
        override fun update(o: Observable?, favoriteActionEventObject: Any?) {
            val favoriteEventAction = (favoriteActionEventObject as? FavoriteActionEvent) ?: return

            if(eventData.id == favoriteEventAction.eventId) {
                toFavoritesImageButton.setImageResource(
                    getFavoriteImageResource(favoriteEventAction.isFavorite)
                )
            }
        }
    }

    private val branchEvent: View =
        view.findViewById(R.id.activity_all_events_event_card)
    private val stateEventTextView: TextView =
        branchEvent.findViewById(R.id.events_card_layout_next_event_text_view)

    private val eventPaddingLeft = branchEvent.paddingLeft
    private val eventPaddingTop = branchEvent.paddingTop
    private val eventPaddingRight = branchEvent.paddingRight
    private val eventPaddingBottom = branchEvent.paddingBottom


    private val dateAndPlaceTextView: TextView =
        branchEvent.findViewById(R.id.events_card_layout_time_place_text_view)
    private val speakerFullNameTextView: TextView =
        branchEvent.findViewById(R.id.events_card_layout_speaker_full_name_text_view)
    private val speakerJobTextView: TextView =
        branchEvent.findViewById(R.id.events_card_layout_job_text_view)
    private val eventTitleTextView: TextView =
        branchEvent.findViewById(R.id.events_card_layout_event_title_text_view)
    private val toFavoritesImageButton: ImageView =
        branchEvent.findViewById(R.id.events_card_layout_to_favorites_image_view)

    private lateinit var eventData: EventData

    init {
        branchEvent.findViewById<TextView>(
            R.id.events_card_layout_next_event_text_view
        ).visibility = View.INVISIBLE
    }

    fun onViewRecycled() {
        favoriteEventActionObservable.unsubscribe(favoriteObserver)
    }

    override fun onBind(data: AllEventsListItem) {
        eventData = (data as? AllEventsListItem.EventListItem)?.data ?: return
        fillEvent(eventData)
        setOnClickListeners(eventData)
        favoriteEventActionObservable.subscribe(favoriteObserver)
    }

    private fun fillEvent(
        eventData: EventData,
    ) {
        dateAndPlaceTextView.text = formatStringForDateAndPlace(eventData)
        speakerFullNameTextView.text = eventData.speaker.fullName
        speakerJobTextView.text = eventData.speaker.job
        eventTitleTextView.text = eventData.title
        setBackgroundEvent(eventData.isCompleted)
        toFavoritesImageButton.setImageResource(getFavoriteImageResource(eventData.isFavorite))
    }

    private fun setBackgroundEvent(isEndEvent: Boolean) {
        if (isEndEvent) {
            stateEventTextView.visibility = View.VISIBLE
            stateEventTextView.setBackgroundResource(R.drawable.bg_text_view_end_event)
            stateEventTextView.text =
                stateEventTextView.context.getString(R.string.activity_all_events_end_event_text)

            branchEvent.setBackgroundResource(R.drawable.bg_unfocused_event_card)
            branchEvent.setPadding(
                eventPaddingLeft,
                eventPaddingTop,
                eventPaddingRight,
                eventPaddingBottom
            )
        } else {
            stateEventTextView.visibility = View.INVISIBLE
            branchEvent.setBackgroundResource(R.drawable.bg_focused_event_card)
            branchEvent.setPadding(
                eventPaddingLeft,
                eventPaddingTop,
                eventPaddingRight,
                eventPaddingBottom
            )
        }
    }

    private fun formatStringForDateAndPlace(event: EventData): String {
        val startTime = event.startTime.getEventFormattedDateTime(DATE_TIME_FORMAT)
        val endTime = event.endTime.getEventFormattedDateTime(DATE_TIME_FORMAT)
        return String.format(
            FORMAT_STRING_FOR_DATE_AND_PLACE,
            startTime,
            endTime,
            event.place
        )
    }

    private fun setOnClickListeners(event: EventData) {

        branchEvent.setOnClickListener {
            allEventsClickListener.onEventClick(
                event
            )
        }

        toFavoritesImageButton.setOnClickListener {
            event.isFavorite = !event.isFavorite
            val favoriteImageResource = getFavoriteImageResource(event.isFavorite)
            toFavoritesImageButton.setImageResource(favoriteImageResource)
            allEventsClickListener.onFavoritesClicked(event)
        }

    }
    private fun getFavoriteImageResource(
        isFavorite: Boolean
    ): Int = when (isFavorite) {
        true -> R.drawable.ic_to_favorites_fill
        else -> R.drawable.ic_to_favorites_border
    }
}