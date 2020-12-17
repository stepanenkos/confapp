package kz.kolesateam.confapp.allevents.presentation.view

import android.view.View
import android.widget.*
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.data.AllEventsListItem
import kz.kolesateam.confapp.presentation.view.BaseViewHolder

class BranchTitleViewHolder(view: View) : BaseViewHolder<AllEventsListItem>(view) {

    private val branchNameTextView: TextView =
        view.findViewById(R.id.activity_all_events_text_view_branch_name)

    override fun onBind(data: AllEventsListItem) {
        branchNameTextView.text = (data as? AllEventsListItem.BranchTitleItem)?.branchTitle
    }
}