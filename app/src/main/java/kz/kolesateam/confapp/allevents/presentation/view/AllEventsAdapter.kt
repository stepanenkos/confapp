package kz.kolesateam.confapp.allevents.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BRANCH_TITLE_TYPE
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.events.presentation.view.EventClickListener

class AllEventsAdapter(
    private val eventClickListener: EventClickListener,
) : RecyclerView.Adapter<BaseViewHolder<UpcomingEventsListItem>>() {
    private val eventsDataList: MutableList<UpcomingEventsListItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<UpcomingEventsListItem> {
        return when (viewType) {
            BRANCH_TITLE_TYPE -> BranchTitleViewHolder(
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.branch_title_layout, parent, false)
            )

            else -> EventsViewHolder(
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.all_events_branch_item, parent, false),
                eventClickListener = eventClickListener
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<UpcomingEventsListItem>, position: Int) {
        holder.onBind(eventsDataList[position])
    }

    override fun getItemCount(): Int = eventsDataList.size

    override fun getItemViewType(position: Int): Int {
        return eventsDataList[position].type
    }

    fun setList(eventsDataList: List<UpcomingEventsListItem>) {
        this.eventsDataList.clear()
        this.eventsDataList.addAll(eventsDataList)
        notifyDataSetChanged()
    }
}