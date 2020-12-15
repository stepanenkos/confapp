package kz.kolesateam.confapp.upcomingevents.presentation.view

import android.view.View
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.upcomingevents.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.presentation.view.BaseViewHolder

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