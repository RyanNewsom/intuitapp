package com.ryannewsom.intuitinterviewapp.ui.issues.list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ryannewsom.intuitinterviewapp.data.source.paged.issues.GithubIssuesFeedDataFactory
import com.ryannewsom.intuitinterviewapp.model.GithubIssue

interface PagedIssuesLiveDataFactory {
    fun buildPagedIssuesLiveData(githubIssuesFeedDataFactory: GithubIssuesFeedDataFactory) : LiveData<PagedList<GithubIssue>>
}