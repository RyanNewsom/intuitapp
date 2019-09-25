package com.ryannewsom.intuitinterviewapp.data.source.paged.repo

import androidx.lifecycle.MutableLiveData
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepository
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepositoryImpl
import com.ryannewsom.intuitinterviewapp.data.source.GithubRepositoryRemoteDataSource
import com.ryannewsom.intuitinterviewapp.data.source.paged.MyPageKeyedDataSource
import com.ryannewsom.intuitinterviewapp.model.GithubRepository
import com.ryannewsom.intuitinterviewapp.util.DefaultSchedulerProvider
import com.ryannewsom.intuitinterviewapp.util.SchedulerProvider

import timber.log.Timber
import java.lang.Exception

class GithubReposPagedDataSource(
    loadingLiveData: MutableLiveData<Boolean>,
    errorLiveData: MutableLiveData<Exception?>,
    private val githubRepoRepository: GithubRepoRepository = GithubRepoRepositoryImpl.getInstance(
        GithubRepositoryRemoteDataSource()
    ),
    schedulerProvider: SchedulerProvider = DefaultSchedulerProvider()
) : MyPageKeyedDataSource<GithubRepository>(
    loadingLiveData,
    errorLiveData,
    schedulerProvider
) {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GithubRepository>
    ) {
        super.loadInitial(params, callback)

        compositeDisposable.add(githubRepoRepository.getRepositories(page = 1)
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
                callback.onResult(it, null, 2)
            }, {
                Timber.e(it, "Unable to fetch repositories")
                errorLiveData.value = it as Exception
            })
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GithubRepository>
    ) {
        super.loadAfter(params, callback)

        compositeDisposable.add(githubRepoRepository.getRepositories(page = params.key)
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