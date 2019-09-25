package com.ryannewsom.intuitinterviewapp.data.http

import com.ryannewsom.intuitinterviewapp.model.GithubIssue
import com.ryannewsom.intuitinterviewapp.model.GithubRepository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubRepositoryService {
    @GET("users/{user}/repos")
    fun getRepos(@Path("user")user: String, @Query("page") page: Int) : Call<List<GithubRepository>>
    @GET
    fun getIssues(@Url url: String,  @Query("page") page: Int, @Query("state") state: String = "all") : Call<List<GithubIssue>>
}