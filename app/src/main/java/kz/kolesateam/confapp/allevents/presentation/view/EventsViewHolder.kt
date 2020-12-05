package kz.kolesateam.confapp.allevents.presentation.view

import android.view.View
import android.widget.*
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.data.AllEventsListItem
import kz.kolesateam.confapp.events.presentation.models.EventData
import kz.kolesateam.confapp.events.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.events.presentation.view.EventClickListener
import java.text.SimpleDateFormat
import java.util.*

class EventsViewHolder(
    view: View,
    private val eventClickListener: EventClickListener,
) : BaseViewHolder<AllEventsListItem>(view) {

    private val branchEvent: View =
        view.findViewById(R.id.activity_all_events_event_card)
    private val stateEventTextView: TextView =
        branchEvent.findViewById(R.id.event_text_view_next_event)
    
    private val eventPaddingLeft = branchEvent.paddingLeft
    private val eventPaddingTop = branchEvent.paddingTop
    private val eventPaddingRight = branchEvent.paddingRight
    private val eventPaddingBottom = branchEvent.paddingBottom


    private val dateAndPlaceTextView: TextView =
        branchEvent.findViewById(R.id.event_text_view_time_place)
    private val speakerFullNameTextView: TextView =
        branchEvent.findViewById(R.id.event_text_view_speaker_full_name)
    private val speakerJobTextView: TextView =
        branchEvent.findViewById(R.id.event_text_view_job)
    private val eventTitleTextView: TextView =
        branchEvent.findViewById(R.id.event_text_view_event_title)
    private val toFavoritesImageButton: ToggleButton =
        branchEvent.findViewById(R.id.event_image_to_favorites)

    init {
        branchEvent.findViewById<TextView>(
            R.id.event_text_view_next_event
        ).visibility = View.INVISIBLE
    }

    override fun onBind(data: AllEventsListItem) {
        val event = (data as? AllEventsListItem.EventListItem)?.data ?: return
        fillEvent(event)
        setOnClickListeners(event)
    }

    private fun fillEvent(
        eventData: EventData,
    ) {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ROOT)
        val dateNowFormat = simpleDateFormat.format(Date())
        val dateNow = simpleDateFormat.parse(dateNowFormat)!!
        dateAndPlaceTextView.text = formatStringForDateAndPlace(eventData)
        speakerFullNameTextView.text = eventData.speaker.fullName
        speakerJobTextView.text = eventData.speaker.job
        eventTitleTextView.text = eventData.title
        setBackgroundEvent(dateNow.after(eventData.endTime))
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
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ROOT)
        val startTime = simpleDateFormat.format(event.startTime)
        val endTime = simpleDateFormat.format(event.endTime)
        return String.format(
            "%s - %s • %s",
            startTime,
            endTime,
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