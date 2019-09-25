package com.ryannewsom.intuitinterviewapp.data.source

import com.ryannewsom.intuitinterviewapp.data.Result
import com.ryannewsom.intuitinterviewapp.model.GithubIssue
import com.ryannewsom.intuitinterviewapp.model.GithubRepository

interface GithubRepositoryDataSource {
    fun getGithubRepository(user: String, page: Int): Result<List<GithubRepository>>
    fun getIssues(issuesUrl: String, page: Int): Result<List<GithubIssue>>
}