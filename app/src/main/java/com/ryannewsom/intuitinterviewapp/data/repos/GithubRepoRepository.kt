package com.ryannewsom.intuitinterviewapp.data.repos

import com.ryannewsom.intuitinterviewapp.model.GithubIssue
import com.ryannewsom.intuitinterviewapp.model.GithubRepository
import io.reactivex.Single

interface GithubRepoRepository {
    fun getRepositories(user: String = "intuit", page: Int) : Single<List<GithubRepository>>
    fun getRepository(repoId: Long) : Single<GithubRepository>
    fun getIssues(repoId: Long, page: Int) : Single<List<GithubIssue>>
    fun getIssue(issueId: Long, repoId: Long) : Single<GithubIssue>
}