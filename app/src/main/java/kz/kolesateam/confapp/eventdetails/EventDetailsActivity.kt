package kz.kolesateam.confapp.eventdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kz.kolesateam.confapp.R

class EventDetailsActivity : AppCompatActivity() {
    private lateinit var eventDetailsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        initViews()
    }

    private fun initViews() {
        eventDetailsTextView = findViewById(R.id.activity_event_details_textView)
        val messageFromPush: String? = intent.getStringExtra(PUSH_NOTIFICATION_MESSAGE)
        eventDetailsTextView.text = messageFromPush ?: ""
    }
}