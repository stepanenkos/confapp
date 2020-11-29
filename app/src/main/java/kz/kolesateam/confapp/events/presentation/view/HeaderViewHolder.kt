package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem

class HeaderViewHolder(view: View) : BaseViewHolder<UpcomingEventsListItem>(view) {
    private val helloUserTextView: TextView =
        view.findViewById(R.id.activity_upcoming_events_text_view_hello_user)

    override fun onBind(data: UpcomingEventsListItem) {
        val userName: String = (data as? UpcomingEventsListItem.HeaderItem)?.userName ?: return

        helloUserTextView.text = helloUserTextView.resources.getString(
            R.string.activity_upcoming_events_text_view_hello_user,
            userName
        )
    }
}