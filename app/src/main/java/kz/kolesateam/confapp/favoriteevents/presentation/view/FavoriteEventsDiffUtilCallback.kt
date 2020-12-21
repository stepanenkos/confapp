package kz.kolesateam.confapp.favoriteevents.presentation.view

import androidx.recyclerview.widget.DiffUtil
import kz.kolesateam.confapp.models.EventData

class FavoriteEventsDiffUtilCallback(
    private val oldlist: List<EventData>,
    private val newList: List<EventData>
) : DiffUtil.Callback(){

    override fun getOldListSize(): Int = oldlist.size


    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition] == newList[newItemPosition]
    }
}