package com.ryannewsom.intuitinterviewapp.data.source.paged

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import java.lang.Exception

abstract class MyFeedDataFactory<T>(
    protected val loadingLiveData: MutableLiveData<Boolean>,
    protected val errorLiveData: MutableLiveData<Exception?>
): DataSource.Factory<Int, T>()