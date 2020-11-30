package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.*
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.presentation.models.EventData

class EventsViewHolder(
    view: View,
    private val eventClickListener: EventClickListener,
) : BaseViewHolder<UpcomingEventsListItem>(view) {

    private val branchEvent: View =
        view.findViewById(R.id.activity_all_events_event_card)

    private val dateAndPlaceTextView: TextView =
        branchEvent.findViewById(R.id.activity_upcoming_events_text_view_time_place)
    private val speakerFullNameTextView: TextView =
        branchEvent.findViewById(R.id.activity_upcoming_events_text_view_speaker_full_name)
    private val speakerJobTextView: TextView =
        branchEvent.findViewById(R.id.activity_upcoming_events_text_view_job)
    private val eventTitleTextView: TextView =
        branchEvent.findViewById(R.id.activity_upcoming_events_text_view_event_title)
    private val toFavoritesImageButton: ToggleButton =
        branchEvent.findViewById(R.id.activity_upcoming_events_image_to_favorites)


    private val eventPaddingTop = branchEvent.paddingTop
    private val eventPaddingBottom = branchEvent.paddingBottom
    private val eventPaddingLeft = branchEvent.paddingLeft
    private val eventPaddingRight = branchEvent.paddingRight

    init {
        branchEvent.findViewById<TextView>(
            R.id.activity_upcoming_events_text_view_next_event
        ).visibility = View.INVISIBLE
    }

    override fun onBind(data: UpcomingEventsListItem) {
        val event = (data as? UpcomingEventsListItem.EventListItem)?.data ?: return
        fillEvent(event)
        setOnClickListeners(event)
    }

    private fun fillEvent(
        eventData: EventData,
    ) {
            dateAndPlaceTextView.text = formatStringForDateAndPlace(eventData)
            speakerFullNameTextView.text = eventData.speaker.fullName
            speakerJobTextView.text = eventData.speaker.job
            eventTitleTextView.text = eventData.title

    }

    private fun formatStringForDateAndPlace(event: EventData): String {
        return String.format(
            "%s - %s • %s",
            event.startTime.substringBeforeLast(":"),
            event.endTime.substringBeforeLast(":"),
            event.place
        )
    }

    private fun setOnClickListeners(event: EventData) {

        branchEvent.setOnClickListener {
            eventClickListener.onEventClick(
                it,
                event.title
            )
        }

        toFavoritesImageButton.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                Toast.makeText(button.context, "CHECKED", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(button.context, "UNCHECKED", Toast.LENGTH_SHORT).show()

            }
        }

    }
}