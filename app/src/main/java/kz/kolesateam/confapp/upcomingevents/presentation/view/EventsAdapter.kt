package kz.kolesateam.confapp.upcomingevents.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.favoriteevents.domain.FavoriteEventActionObservable
import kz.kolesateam.confapp.upcomingevents.data.models.HEADER_TYPE
import kz.kolesateam.confapp.upcomingevents.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.presentation.listeners.UpcomingItemsClickListener
import kz.kolesateam.confapp.presentation.view.BaseViewHolder

class EventsAdapter(
    private val upcomingItemsClickListener: UpcomingItemsClickListener,
    private val favoriteEventActionObservable: FavoriteEventActionObservable,
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

            else -> BranchViewHolder(
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.branch_item, parent, false),
                upcomingItemsClickListener = upcomingItemsClickListener,
                favoriteEventActionObservable = favoriteEventActionObservable,
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