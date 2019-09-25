package com.ryannewsom.intuitinterviewapp.ui.repository.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ryannewsom.intuitinterviewapp.model.GithubRepository
import com.ryannewsom.intuitinterviewapp.data.source.paged.repo.GithubRepositoriesFeedDataFactory
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception


class RepositoryListViewModel(
    pagedRepositoryLiveDataFactory: PagedRepositoryLiveDataFactory = PagedRepositoryLiveDataFactoryImpl()
) : ViewModel() {
    private val disposables = CompositeDisposable()
    val repositories: LiveData<PagedList<GithubRepository>>
    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading
    private val _error = MutableLiveData<Exception?>()
    val error : LiveData<Exception?> = _error
    private val githubRepositoriesFeedDataFactory: GithubRepositoriesFeedDataFactory

    init {
        githubRepositoriesFeedDataFactory =
            GithubRepositoriesFeedDataFactory(
                _loading,
                _error
            )

        repositories = pagedRepositoryLiveDataFactory.buildPagedRepositoryLiveData(githubRepositoriesFeedDataFactory)
    }

    fun retry() {
        _error.value = null
        githubRepositoriesFeedDataFactory.githubReposPagedDataSource.retry()
    }

    override fun onCleared() {
        disposables.clear()
        githubRepositoriesFeedDataFactory.githubReposPagedDataSource.clear()
        super.onCleared()
    }
}
