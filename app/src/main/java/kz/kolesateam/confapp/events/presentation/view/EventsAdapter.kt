package kz.kolesateam.confapp.events.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.presentation.view.BranchTitleViewHolder
import kz.kolesateam.confapp.allevents.presentation.view.EventsViewHolder
import kz.kolesateam.confapp.events.data.models.BRANCH_TITLE_TYPE
import kz.kolesateam.confapp.events.data.models.EVENT_TYPE
import kz.kolesateam.confapp.events.data.models.HEADER_TYPE
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem

class EventsAdapter(
    private val eventClickListener: EventClickListener,
) : RecyclerView.Adapter<BaseViewHolder<UpcomingEventsListItem>>() {
    private val branchDataList: MutableList<UpcomingEventsListItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<UpcomingEventsListItem> {
        return when (viewType) {
            HEADER_TYPE -> HeaderViewHolder(
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_layout, parent, false)
            )
            EVENT_TYPE -> EventsViewHolder(
                view = LayoutInflater.from(parent.context)
                .inflate(R.layout.all_events_branch_item, parent, false),
                eventClickListener = eventClickListener
            )

            BRANCH_TITLE_TYPE -> BranchTitleViewHolder(
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.branch_title_layout, parent, false)
            )

            else -> BranchViewHolder(
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.branch_item, parent, false),
                eventClickListener = eventClickListener
            )

        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<UpcomingEventsListItem>, position: Int) {
        holder.onBind(branchDataList[position])
    }

    override fun getItemCount(): Int = branchDataList.size

    override fun getItemViewType(position: Int): Int {
        return branchDataList[position].type
    }

    fun setList(branchDataList: List<UpcomingEventsListItem>) {
        this.branchDataList.clear()
        this.branchDataList.addAll(branchDataList)
        notifyDataSetChanged()
    }
}