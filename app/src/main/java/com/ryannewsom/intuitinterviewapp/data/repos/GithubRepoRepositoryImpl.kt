package com.ryannewsom.intuitinterviewapp.data.repos

import com.ryannewsom.intuitinterviewapp.data.Result
import com.ryannewsom.intuitinterviewapp.data.source.GithubRepositoryDataSource
import com.ryannewsom.intuitinterviewapp.data.succeeded
import com.ryannewsom.intuitinterviewapp.model.GithubIssue
import com.ryannewsom.intuitinterviewapp.model.GithubRepository
import com.ryannewsom.intuitinterviewapp.util.SingletonHolderOne
import io.reactivex.Single
import java.lang.IllegalStateException

class GithubRepoRepositoryImpl private constructor(
    private val dataSource: GithubRepositoryDataSource
) : GithubRepoRepository {
    private var repositoriesMap = mutableMapOf<Long, GithubRepository>()
    private var issuesMap = mutableMapOf<Long, MutableList<GithubIssue>>()

    override fun getRepositories(user: String, page: Int): Single<List<GithubRepository>> {
        return Single.create<List<GithubRepository>> { emitter ->

            val result = dataSource.getGithubRepository(user, page)
            if (result is Result.Success && result.succeeded) {
                result.data.forEach {
                    repositoriesMap[it.id] = it
                }
                emitter.onSuccess(result.data)
            } else if (result is Result.Error) {
                emitter.tryOnError(result.exception)
            } else {
                emitter.tryOnError(IllegalStateException())
            }
        }
    }

    override fun getRepository(repoId: Long): Single<GithubRepository> {
        return Single.just(repositoriesMap[repoId])
    }

    override fun getIssues(repoId: Long, page: Int): Single<List<GithubIssue>> {
        return Single.create { emitter ->

            val repo = repositoriesMap[repoId] ?: throw IllegalStateException()
            val result = dataSource.getIssues(repo.issuesUrl.removeSuffix("{/number}"), page)
            if (result is Result.Success && result.succeeded) {
                val issues = issuesMap[repoId]
                if (issues != null) {
                    issues.addAll(result.data)
                } else {
                    issuesMap[repoId] = result.data.toMutableList()
                }
                emitter.onSuccess(result.data)
            } else if (result is Result.Error) {
                emitter.tryOnError(result.exception)
            } else {
                emitter.tryOnError(IllegalStateException())
            }
        }
    }

    override fun getIssue(issueId: Long, repoId: Long): Single<GithubIssue> {
        return Single.just(issuesMap[repoId]?.find { it.id == issueId })
    }

    companion object :
        SingletonHolderOne<GithubRepoRepositoryImpl, GithubRepositoryDataSource>(::GithubRepoRepositoryImpl)
}