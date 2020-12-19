package kz.kolesateam.confapp.allevents.presentation.view

import androidx.recyclerview.widget.DiffUtil
import kz.kolesateam.confapp.allevents.data.AllEventsListItem

class AllEventsDiffUtilCalback(
    private val oldList: List<AllEventsListItem>,
    private val newList: List<AllEventsListItem>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEvent =
            (oldList[oldItemPosition] as? AllEventsListItem.EventListItem)?.data ?: return false
        val newEvent =
            (newList[newItemPosition] as? AllEventsListItem.EventListItem)?.data ?: return false
        return oldEvent.id == newEvent.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEvent =
            (oldList[oldItemPosition] as? AllEventsListItem.EventListItem)?.data ?: return false
        val newEvent =
            (newList[newItemPosition] as? AllEventsListItem.EventListItem)?.data ?: return false
        return oldEvent == newEvent
    }

}