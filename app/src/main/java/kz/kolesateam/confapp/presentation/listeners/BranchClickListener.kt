package kz.kolesateam.confapp.presentation.listeners

import kz.kolesateam.confapp.models.BranchData

interface BranchClickListener {
    fun onBranchClick(
        branchData: BranchData,
    )
}