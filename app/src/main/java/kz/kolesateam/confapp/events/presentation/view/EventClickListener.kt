package kz.kolesateam.confapp.events.presentation.view

import android.view.View

interface EventClickListener {
    fun onEventClick(
        view: View,
        eventTitle: String
    )

    fun onBranchClick(
        view: View,
        branchTitle: String,
    )
}