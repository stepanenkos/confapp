package kz.kolesateam.confapp.events.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem

class UpcomingEventsAdapter(
    private val eventClickListener: EventClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val branchDataList: MutableList<UpcomingEventsListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> HeaderViewHolder(
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_layout, parent, false)
            )
            else -> BranchViewHolder(
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.branch_item, parent, false),
                eventClickListener = eventClickListener
            )

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.onBind(branchDataList[position].data as String)
        }

        if (holder is BranchViewHolder) {
            holder.onBind(branchDataList[position].data as BranchApiData)
        }
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