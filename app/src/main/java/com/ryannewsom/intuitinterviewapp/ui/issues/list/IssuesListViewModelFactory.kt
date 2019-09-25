package com.ryannewsom.intuitinterviewapp.ui.issues.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class IssuesListViewModelFactory(
    private val repoId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IssuesListViewModel::class.java)) {
            return IssuesListViewModel(
                repoId
            ) as T
        } else {
            throw IllegalArgumentException("You must provide an implementation in the factory for this view model")
        }
    }
}