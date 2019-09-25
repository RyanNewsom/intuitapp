package com.ryannewsom.intuitinterviewapp.ui.repository.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepository
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepositoryImpl
import com.ryannewsom.intuitinterviewapp.data.source.GithubRepositoryRemoteDataSource

class RepositoryListViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RepositoryListViewModel::class.java)) {
            return RepositoryListViewModel() as T
        } else {
            throw IllegalArgumentException("You must provide an implementation in the factory for this view model")
        }
    }
}