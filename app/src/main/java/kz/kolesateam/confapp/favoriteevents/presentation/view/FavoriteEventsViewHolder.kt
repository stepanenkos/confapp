package kz.kolesateam.confapp.favoriteevents.presentation.view

import android.view.View
import android.widget.*
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.presentation.listeners.AllEventsClickListener
import java.text.SimpleDateFormat
import java.util.*
import kz.kolesateam.confapp.favoriteevents.domain.FavoriteEventActionObservable
import kz.kolesateam.confapp.favoriteevents.domain.model.FavoriteActionEvent

class FavoriteEventsViewHolder(
    view: View,
    private val allEventsClickListener: AllEventsClickListener,
    private val favoriteEventActionObservable: FavoriteEventActionObservable
) : BaseViewHolder<EventData>(view) {
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
    private lateinit var eventData: EventData

    init {
        favoriteEvent.findViewById<TextView>(
            R.id.events_card_layout_next_event_text_view
        ).visibility = View.INVISIBLE
    }

    override fun onBind(eventData: EventData) {
        this.eventData = eventData
        fillEvent(eventData)
        setOnClickListeners(eventData)
        favoriteEventActionObservable.subscribe(favoriteObserver)
    }

    fun onViewRecycled() {
        favoriteEventActionObservable.unsubscribe(favoriteObserver)
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