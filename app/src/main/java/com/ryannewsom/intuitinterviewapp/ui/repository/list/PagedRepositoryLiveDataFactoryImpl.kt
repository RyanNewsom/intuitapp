package com.ryannewsom.intuitinterviewapp.ui.repository.list

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ryannewsom.intuitinterviewapp.data.source.paged.repo.GithubRepositoriesFeedDataFactory
import com.ryannewsom.intuitinterviewapp.model.GithubRepository

class PagedRepositoryLiveDataFactoryImpl : PagedRepositoryLiveDataFactory {
    override fun buildPagedRepositoryLiveData(githubRepositoriesFeedDataFactory: GithubRepositoriesFeedDataFactory): LiveData<PagedList<GithubRepository>> {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(30)
            .setPrefetchDistance(20)
            .build()

        return LivePagedListBuilder(githubRepositoriesFeedDataFactory, pagedListConfig).build()
    }
}