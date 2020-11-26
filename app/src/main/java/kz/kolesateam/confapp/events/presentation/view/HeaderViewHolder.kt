package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val helloUserTextView: TextView =
        view.findViewById(R.id.activity_upcoming_events_text_view_hello_user)

    fun onBind(userName: String) {
        helloUserTextView.text = helloUserTextView.resources.getString(
            R.string.activity_upcoming_events_text_view_hello_user,
            userName
        )
    }
}