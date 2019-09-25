package com.ryannewsom.intuitinterviewapp.data.source.paged.issues

import androidx.lifecycle.MutableLiveData
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepository
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepositoryImpl
import com.ryannewsom.intuitinterviewapp.data.source.GithubRepositoryRemoteDataSource
import com.ryannewsom.intuitinterviewapp.data.source.paged.MyPageKeyedDataSource
import com.ryannewsom.intuitinterviewapp.model.GithubIssue
import com.ryannewsom.intuitinterviewapp.util.DefaultSchedulerProvider
import com.ryannewsom.intuitinterviewapp.util.SchedulerProvider
import timber.log.Timber
import java.lang.Exception

class GithubIssuesPagedDataSource(
    loadingLiveData: MutableLiveData<Boolean>,
    errorLiveData: MutableLiveData<Exception?>,
    private val repoId: Long,
    private val noIssues: MutableLiveData<Boolean>,
    private val githubRepoRepository: GithubRepoRepository = GithubRepoRepositoryImpl.getInstance(
        GithubRepositoryRemoteDataSource()
    ),
    schedulerProvider: SchedulerProvider = DefaultSchedulerProvider()
) : MyPageKeyedDataSource<GithubIssue>(
    loadingLiveData,
    errorLiveData,
    schedulerProvider
) {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GithubIssue>
    ) {
        super.loadInitial(params, callback)

        compositeDisposable.add(githubRepoRepository.getIssues(repoId, page = 1)
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .doOnSubscribe {
                loadingLiveData.postValue(true)
            }
            .doFinally {
                loadingLiveData.value = false
            }
            .subscribe({
                Timber.i("Loaded initial repos")
                if (it.isEmpty()) {
                    noIssues.postValue(true)
                }
                callback.onResult(it, null, 2)
            }, {
                Timber.e(it, "Unable to fetch repositories")
                errorLiveData.value = it as Exception
            })
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GithubIssue>
    ) {
        super.loadAfter(params, callback)

        compositeDisposable.add(githubRepoRepository.getIssues(repoId, page = params.key)
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .doOnSubscribe {
                loadingLiveData.postValue(true)
            }
            .doFinally {
                loadingLiveData.value = false
            }
            .subscribe({
                callback.onResult(it, params.key + 1)
            }, {
                Timber.e(it, "Unable to fetch repositories")
                errorLiveData.value = it as Exception
            })
        )
    }
}