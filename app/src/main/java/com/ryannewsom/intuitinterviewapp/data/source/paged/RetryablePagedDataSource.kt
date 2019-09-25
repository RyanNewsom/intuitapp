package com.ryannewsom.intuitinterviewapp.data.source.paged

interface RetryablePagedDataSource {
    fun retry()
    fun clear()
}