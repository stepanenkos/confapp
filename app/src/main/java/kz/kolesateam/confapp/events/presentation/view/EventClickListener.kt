package kz.kolesateam.confapp.events.presentation.view

import android.view.View

interface EventClickListener {
    fun onEventClick(
        view: View,
        branchTitle: String,
        eventTitle: String
    )

    fun onBranchClick(
        view: View,
        branchTitle: String,
    )

    fun onFavoritesImageClick(
        view: View
    )
}