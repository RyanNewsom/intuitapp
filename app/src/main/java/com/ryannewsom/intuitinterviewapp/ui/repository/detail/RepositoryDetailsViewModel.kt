package com.ryannewsom.intuitinterviewapp.ui.repository.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepository
import com.ryannewsom.intuitinterviewapp.model.GithubRepository
import com.ryannewsom.intuitinterviewapp.util.DefaultSchedulerProvider
import com.ryannewsom.intuitinterviewapp.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.lang.Exception

class RepositoryDetailsViewModel(
    private val githubRepoRepository: GithubRepoRepository,
    val repoId: Long,
    private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider()
) : ViewModel() {
    private val _repository = MutableLiveData<GithubRepository>()
    val repository: LiveData<GithubRepository> = _repository
    private val _error = MutableLiveData<Exception?>()
    val error: LiveData<Exception?> = _error
    private val disposables = CompositeDisposable()

    init {
        fetchRepository()
    }

    fun retry() {
        _error.value = null
        fetchRepository()
    }

    private fun fetchRepository() {
        disposables.add(
            githubRepoRepository.getRepository(repoId)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    _repository.value = it
                }, {
                    _error.value = it as Exception
                    Timber.e(it, "Failed to fetch repository $repoId")
                })
        )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
