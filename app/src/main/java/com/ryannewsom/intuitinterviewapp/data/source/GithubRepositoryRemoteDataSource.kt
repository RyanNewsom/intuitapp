package com.ryannewsom.intuitinterviewapp.data.source

import com.ryannewsom.intuitinterviewapp.data.Result
import com.ryannewsom.intuitinterviewapp.data.http.GithubRepositoryService
import com.ryannewsom.intuitinterviewapp.data.http.HttpRequestFailedException
import com.ryannewsom.intuitinterviewapp.model.GithubIssue
import com.ryannewsom.intuitinterviewapp.model.GithubRepository
import timber.log.Timber
import java.lang.Exception

class GithubRepositoryRemoteDataSource : GithubRemoteDataSource(), GithubRepositoryDataSource {
    override fun getGithubRepository(user: String, page: Int): Result<List<GithubRepository>> {
        Timber.i("Fetching github repositories for user: $user, page: $page")
        var data: List<GithubRepository> = listOf()
        var requestError: Exception? = null

        try {
            val service = retrofit.create(GithubRepositoryService::class.java)
            val response = service.getRepos(user, page).execute()
            if (response.isSuccessful) {
                data = response.body() ?: listOf()
            } else {
                requestError = HttpRequestFailedException(response.code())
            }
        } catch (error: Exception) {
            requestError = error
            Timber.e(error, "Failed to fetch github repositories")
        }

        return if (requestError == null) {
            Result.Success(data)
        } else {
            Result.Error(requestError)
        }
    }

    override fun getIssues(issuesUrl: String, page: Int): Result<List<GithubIssue>> {
        Timber.i("Fetching github issues for repo. page: $page Url: $issuesUrl")

        var data: List<GithubIssue> = listOf()
        var requestError: Exception? = null

        try {
            val service = retrofit.create(GithubRepositoryService::class.java)
            val response = service.getIssues(issuesUrl, page).execute()
            if (response.isSuccessful) {
                data = response.body() ?: listOf()
                data = data.filter { !it.isPullRequest() }
            } else {
                requestError = HttpRequestFailedException(response.code())
            }
        } catch (error: Exception) {
            requestError = error
            Timber.e(error, "Failed to fetch github repositories")
        }

        return if (requestError == null) {
            Result.Success(data)
        } else {
            Result.Error(requestError)
        }
    }
}