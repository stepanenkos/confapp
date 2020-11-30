package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.*
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem

class BranchTitleViewHolder(view: View) : BaseViewHolder<UpcomingEventsListItem>(view) {

    private val branchNameTextView: TextView =
        view.findViewById(R.id.activity_all_events_text_view_branch_name)


    override fun onBind(data: UpcomingEventsListItem) {
        branchNameTextView.text = (data as? UpcomingEventsListItem.BranchTitleItem)?.branchTitle
    }
}