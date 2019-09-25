package com.ryannewsom.intuitinterviewapp.data.source.paged.issues

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ryannewsom.intuitinterviewapp.data.source.paged.MyFeedDataFactory
import com.ryannewsom.intuitinterviewapp.data.source.paged.RetryablePagedDataSource
import com.ryannewsom.intuitinterviewapp.model.GithubIssue
import java.lang.Exception

class GithubIssuesFeedDataFactory(
    loadingLiveData: MutableLiveData<Boolean>,
    errorLiveData: MutableLiveData<Exception?>,
    private val repoId: Long,
    private val noIssues: MutableLiveData<Boolean>
) : MyFeedDataFactory<GithubIssue>(loadingLiveData, errorLiveData) {
    lateinit var retryableDataSource: RetryablePagedDataSource

    override fun create(): DataSource<Int, GithubIssue> {
        retryableDataSource =
            GithubIssuesPagedDataSource(
                loadingLiveData,
                errorLiveData,
                repoId,
                noIssues
            )
        return retryableDataSource as GithubIssuesPagedDataSource
    }
}