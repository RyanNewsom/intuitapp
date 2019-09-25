package com.ryannewsom.intuitinterviewapp.data.source.paged.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ryannewsom.intuitinterviewapp.data.source.paged.MyFeedDataFactory
import com.ryannewsom.intuitinterviewapp.data.source.paged.RetryablePagedDataSource
import com.ryannewsom.intuitinterviewapp.model.GithubRepository
import java.lang.Exception

class GithubRepositoriesFeedDataFactory(
    loadingLiveData: MutableLiveData<Boolean>,
    errorLiveData: MutableLiveData<Exception?>
) : MyFeedDataFactory<GithubRepository>(loadingLiveData, errorLiveData) {
    lateinit var githubReposPagedDataSource: RetryablePagedDataSource

    override fun create(): DataSource<Int, GithubRepository> {
        githubReposPagedDataSource =
            GithubReposPagedDataSource(
                loadingLiveData,
                errorLiveData
            )
        return githubReposPagedDataSource as GithubReposPagedDataSource
    }
}