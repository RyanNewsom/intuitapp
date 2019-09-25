package com.ryannewsom.intuitinterviewapp.ui.issues.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ryannewsom.intuitinterviewapp.data.source.paged.issues.GithubIssuesFeedDataFactory
import com.ryannewsom.intuitinterviewapp.model.GithubIssue

class IssuesListViewModel(
    val repoId: Long,
    pagedIssuesLiveDataFactory: PagedIssuesLiveDataFactory = PagedIssuesLiveDataFactoryImpl()
) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _error = MutableLiveData<Exception?>()
    val error: LiveData<Exception?> = _error
    val issues: LiveData<PagedList<GithubIssue>>
    private val _noIssues = MutableLiveData<Boolean>()
    val noIssues: LiveData<Boolean> = _noIssues
    private val githubIssuesFeedDataFactory: GithubIssuesFeedDataFactory

    init {
        githubIssuesFeedDataFactory =
            GithubIssuesFeedDataFactory(
                _loading,
                _error,
                repoId,
                _noIssues
            )
        issues = pagedIssuesLiveDataFactory.buildPagedIssuesLiveData(githubIssuesFeedDataFactory)
    }

    fun retry() {
        _error.value = null
        githubIssuesFeedDataFactory.retryableDataSource.retry()
    }

    override fun onCleared() {
        githubIssuesFeedDataFactory.retryableDataSource.clear()
        super.onCleared()
    }
}
