package kz.kolesateam.confapp.eventdetails.presentation

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.presentation.listeners.FavoritesClickListener
import kz.kolesateam.confapp.utils.extensions.zoned_date_time.getEventFormattedDateTime
import org.koin.android.ext.android.inject

private const val FORMAT_STRING_FOR_DATE_AND_PLACE = "%s - %s • %s"
private const val DATE_TIME_FORMAT = "HH:mm"
private const val DEFAULT_EVENT_ID = 0

class EventDetailsActivity : AppCompatActivity(), FavoritesClickListener {
    private val eventDetailsViewModel: EventDetailsViewModel by inject()

    private lateinit var buttonGoBack: ImageView
    private lateinit var buttonToFavorites: ImageView
    private lateinit var imageViewSpeakerPhoto: ImageView
    private lateinit var textViewInvitedSpeaker: TextView
    private lateinit var textViewSpeakerFullName: TextView
    private lateinit var textViewSpeakerJob: TextView
    private lateinit var textViewEventTimeAndPlace: TextView
    private lateinit var textViewEventTitle: TextView
    private lateinit var textViewEventDescription: TextView

    private var eventId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        initViews()
        observeEventDetailsViewModel()
        eventDetailsViewModel.onStart(eventId)
    }

    private fun initViews() {
        buttonGoBack = findViewById(R.id.activity_event_details_button_go_back)
        buttonToFavorites = findViewById(R.id.activity_event_details_image_view_to_favorites)
        imageViewSpeakerPhoto = findViewById(R.id.activity_event_details_image_view_speaker_photo)
        textViewInvitedSpeaker =
            findViewById(R.id.activity_event_details_text_view_speaker_is_invited)
        textViewSpeakerFullName =
            findViewById(R.id.activity_event_details_text_view_speaker_full_name)
        textViewSpeakerJob = findViewById(R.id.activity_event_details_text_view_speaker_job)
        textViewEventTimeAndPlace =
            findViewById(R.id.activity_event_details_text_view_time_and_place)
        textViewEventTitle = findViewById(R.id.activity_event_details_text_view_event_title)
        textViewEventDescription =
            findViewById(R.id.activity_event_details_text_view_event_description)
        eventId = intent.getIntExtra(EVENT_ID, DEFAULT_EVENT_ID)

        buttonGoBack.setOnClickListener {
            finish()
        }
    }

    private fun setListeners(eventData: EventData) {
        buttonToFavorites.setOnClickListener {
            eventData.isFavorite = !eventData.isFavorite
            val favoriteImageResource = getFavoriteImageResource(eventData.isFavorite)
            buttonToFavorites.setImageResource(favoriteImageResource)
            eventDetailsViewModel.onFavoriteClick(eventData)
        }
    }

    private fun observeEventDetailsViewModel() {
        eventDetailsViewModel.getEventDetailsLiveData().observe(this, ::showResult)
        eventDetailsViewModel.getErrorLiveData().observe(this, ::showError)
    }

    private fun showResult(eventData: EventData) {
        prepareLayout(eventData)
    }

    private fun showError(errorMessage: Exception) {
        Toast.makeText(this, errorMessage.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun prepareLayout(eventData: EventData) {
        if (eventData.speaker.isInvited) {
            textViewInvitedSpeaker.visibility = View.VISIBLE
            textViewInvitedSpeaker.text =
                resources.getText(R.string.activity_event_details_speaker_invited)
        } else {
            textViewInvitedSpeaker.visibility = View.INVISIBLE
        }

        buttonToFavorites.setImageResource(getFavoriteImageResource(eventData.isFavorite))

        Glide.with(imageViewSpeakerPhoto.context)
            .load(eventData.speaker.photoUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    imageViewSpeakerPhoto.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    imageViewSpeakerPhoto.visibility = View.VISIBLE
                    return false
                }

            })
            .into(imageViewSpeakerPhoto)

        textViewSpeakerFullName.text = eventData.speaker.fullName
        textViewSpeakerJob.text = eventData.speaker.job
        textViewEventTimeAndPlace.text = formatStringForDateAndPlace(eventData)
        textViewEventTitle.text = eventData.title
        textViewEventDescription.text = eventData.description
        setListeners(eventData)
    }

    private fun getFavoriteImageResource(
        isFavorite: Boolean,
    ): Int = when (isFavorite) {
        true -> R.drawable.ic_activity_event_details_to_favorites_fill
        else -> R.drawable.ic_activity_event_details_to_favorites_border
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

    override fun onFavoritesClicked(eventData: EventData) {
        eventDetailsViewModel.onFavoriteClick(eventData)
    }
}