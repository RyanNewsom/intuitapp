package com.ryannewsom.intuitinterviewapp.ui.issues.list

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ryannewsom.intuitinterviewapp.data.source.paged.issues.GithubIssuesFeedDataFactory
import com.ryannewsom.intuitinterviewapp.model.GithubIssue

class PagedIssuesLiveDataFactoryImpl : PagedIssuesLiveDataFactory {
    override fun buildPagedIssuesLiveData(githubIssuesFeedDataFactory: GithubIssuesFeedDataFactory): LiveData<PagedList<GithubIssue>> {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(30)
            .setPrefetchDistance(20)
            .build()

        return LivePagedListBuilder(githubIssuesFeedDataFactory, pagedListConfig).build()
    }
}