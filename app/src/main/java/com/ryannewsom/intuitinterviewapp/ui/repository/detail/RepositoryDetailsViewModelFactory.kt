package com.ryannewsom.intuitinterviewapp.ui.repository.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepository
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepositoryImpl
import com.ryannewsom.intuitinterviewapp.data.source.GithubRepositoryRemoteDataSource

class RepositoryDetailsViewModelFactory(
    private val githubRepoRepository: GithubRepoRepository = GithubRepoRepositoryImpl.getInstance(
        GithubRepositoryRemoteDataSource()
    ),
    private val repoId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoryDetailsViewModel::class.java)) {
            return RepositoryDetailsViewModel(
                githubRepoRepository,
                repoId
            ) as T
        } else {
            throw IllegalArgumentException("You must provide an implementation in the factory for this view model")
        }
    }
}