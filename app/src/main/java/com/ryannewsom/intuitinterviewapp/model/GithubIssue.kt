package com.ryannewsom.intuitinterviewapp.model

import com.google.gson.annotations.SerializedName

data class GithubIssue(
    val id: Long,
    val number: Int,
    val title: String,
    val state: String,
    val locked: String,
    val body: String,
    @SerializedName("pull_request")
    private val pullRequest: Any?
) {
    fun isPullRequest() : Boolean {
        return pullRequest != null
    }
}