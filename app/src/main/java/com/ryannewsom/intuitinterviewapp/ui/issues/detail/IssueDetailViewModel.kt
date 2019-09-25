package com.ryannewsom.intuitinterviewapp.ui.issues.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ryannewsom.intuitinterviewapp.data.repos.GithubRepoRepository
import com.ryannewsom.intuitinterviewapp.model.GithubIssue
import com.ryannewsom.intuitinterviewapp.model.GithubRepository
import com.ryannewsom.intuitinterviewapp.util.DefaultSchedulerProvider
import com.ryannewsom.intuitinterviewapp.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.lang.Exception

class IssueDetailViewModel(
    private val githubRepoRepository: GithubRepoRepository,
    val repoId: Long,
    val issueId: Long,
    private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider()
) : ViewModel() {
    private val _issue = MutableLiveData<GithubIssue>()
    val issue: LiveData<GithubIssue> = _issue
    private val _error = MutableLiveData<Exception?>()
    val error: LiveData<Exception?> = _error
    private val disposables = CompositeDisposable()

    init {
        fetchIssue()
    }

    fun retry() {
        _error.value = null
        fetchIssue()
    }

    private fun fetchIssue() {
        disposables.add(
            githubRepoRepository.getIssue(issueId, repoId)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    _issue.value = it
                }, {
                    Timber.e(it, "Failed to fetch issue: $issueId")
                    _error.value = it as Exception
                })
        )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
