package com.ryannewsom.intuitinterviewapp.ui.issues.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepository
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepositoryImpl
import com.ryannewsom.intuitinterviewapp.data.source.GithubRepositoryRemoteDataSource

class IssueDetailViewModelFactory(
    private val githubRepoRepository: GithubRepoRepository = GithubRepoRepositoryImpl.getInstance(
        GithubRepositoryRemoteDataSource()
    ),
    private val repoId: Long,
    private val issueId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IssueDetailViewModel::class.java)) {
            return IssueDetailViewModel(
                githubRepoRepository,
                repoId,
                issueId
            ) as T
        } else {
            throw IllegalArgumentException("You must provide an implementation in the factory for this view model")
        }
    }
}