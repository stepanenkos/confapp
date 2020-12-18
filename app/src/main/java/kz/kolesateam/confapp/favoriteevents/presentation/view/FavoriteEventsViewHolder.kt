package kz.kolesateam.confapp.favoriteevents.presentation.view

import android.view.View
import android.widget.*
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.presentation.listeners.AllEventsClickListener
import kz.kolesateam.confapp.utils.extensions.ZonedDateTime.getEventFormattedDateTime
import org.threeten.bp.format.DateTimeFormatter

private const val FORMAT_STRING_FOR_DATE_AND_PLACE = "%s - %s • %s"
private const val DATE_TIME_FORMAT = "HH:mm"

class FavoriteEventsViewHolder(
    view: View,
    private val allEventsClickListener: AllEventsClickListener,
) : BaseViewHolder<EventData>(view) {

    private val favoriteEvent: View =
        view.findViewById(R.id.activity_all_events_event_card)

    private val dateAndPlaceTextView: TextView =
        favoriteEvent.findViewById(R.id.events_card_layout_time_place_text_view)
    private val speakerFullNameTextView: TextView =
        favoriteEvent.findViewById(R.id.events_card_layout_speaker_full_name_text_view)
    private val speakerJobTextView: TextView =
        favoriteEvent.findViewById(R.id.events_card_layout_job_text_view)
    private val eventTitleTextView: TextView =
        favoriteEvent.findViewById(R.id.events_card_layout_event_title_text_view)
    private val toFavoritesImageButton: ImageView =
        favoriteEvent.findViewById(R.id.events_card_layout_to_favorites_image_view)

    init {
        favoriteEvent.findViewById<TextView>(
            R.id.events_card_layout_next_event_text_view
        ).visibility = View.INVISIBLE
    }

    override fun onBind(eventData: EventData) {
        fillEvent(eventData)
        setOnClickListeners(eventData)

    }

    private fun fillEvent(
        eventData: EventData,
    ) {
        val favoriteImageResource = getFavoriteImageResource(eventData.isFavorite)

        dateAndPlaceTextView.text = formatStringForDateAndPlace(eventData)
        speakerFullNameTextView.text = eventData.speaker.fullName
        speakerJobTextView.text = eventData.speaker.job
        eventTitleTextView.text = eventData.title
        toFavoritesImageButton.setImageResource(favoriteImageResource)
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

        favoriteEvent.setOnClickListener {
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