package com.ryannewsom.intuitinterviewapp.ui.repository.list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ryannewsom.intuitinterviewapp.data.source.paged.repo.GithubRepositoriesFeedDataFactory
import com.ryannewsom.intuitinterviewapp.model.GithubRepository

interface PagedRepositoryLiveDataFactory {
    fun buildPagedRepositoryLiveData(githubRepositoryFeedDataFactory: GithubRepositoriesFeedDataFactory) : LiveData<PagedList<GithubRepository>>
}