package kz.kolesateam.confapp.allevents.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.data.AllEventsListItem
import kz.kolesateam.confapp.allevents.data.BRANCH_TITLE_TYPE
import kz.kolesateam.confapp.favoriteevents.domain.FavoriteEventActionObservable
import kz.kolesateam.confapp.presentation.listeners.AllEventsClickListener
import kz.kolesateam.confapp.presentation.view.BaseViewHolder

class AllEventsAdapter(
    private val allEventsClickListener: AllEventsClickListener,
    private val favoriteEventActionObservable: FavoriteEventActionObservable
) : RecyclerView.Adapter<BaseViewHolder<AllEventsListItem>>() {
    private val eventsDataList: MutableList<AllEventsListItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<AllEventsListItem> {
        return when (viewType) {
            BRANCH_TITLE_TYPE -> BranchTitleViewHolder(
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.branch_title_layout, parent, false)
            )

            else -> EventsViewHolder(
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.all_events_branch_item, parent, false),
                allEventsClickListener = allEventsClickListener,
                favoriteEventActionObservable = favoriteEventActionObservable
            )
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder<AllEventsListItem>) {
        super.onViewRecycled(holder)
        (holder as? EventsViewHolder)?.onViewRecycled()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<AllEventsListItem>, position: Int) {
        holder.onBind(eventsDataList[position])
    }

    override fun getItemCount(): Int = eventsDataList.size

    override fun getItemViewType(position: Int): Int {
        return eventsDataList[position].type
    }

    fun setList(eventsDataList: List<AllEventsListItem>) {
        this.eventsDataList.clear()
        this.eventsDataList.addAll(eventsDataList)
        notifyDataSetChanged()
    }
}