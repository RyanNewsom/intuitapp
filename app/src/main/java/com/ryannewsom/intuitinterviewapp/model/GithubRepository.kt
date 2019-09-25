package com.ryannewsom.intuitinterviewapp.model

import com.google.gson.annotations.SerializedName

data class GithubRepository(
    val id: Long,
    val name: String,
    val description: String,
    @SerializedName("issues_url")
    val issuesUrl: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Long,
    @SerializedName("watchers_count")
    val watchersCount: Long,
    @SerializedName("forks_count")
    val forksCount: Long
)