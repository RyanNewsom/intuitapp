package com.ryannewsom.intuitinterviewapp.data.source.paged

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ryannewsom.intuitinterviewapp.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.lang.Exception

abstract class MyPageKeyedDataSource<T>(
    protected val loadingLiveData: MutableLiveData<Boolean>,
    protected val errorLiveData: MutableLiveData<Exception?>,
    protected val schedulerProvider: SchedulerProvider
) : PageKeyedDataSource<Int, T>(),
    RetryablePagedDataSource {
    private var loadInitialParams: LoadInitialParams<Int>? = null
    private var loadInitialCallback: LoadInitialCallback<Int, T>? = null
    private var loadAfterParams: LoadParams<Int>? = null
    private var loadAfterCallback: LoadCallback<Int, T>? = null
    protected val compositeDisposable = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        Timber.i("Load initial")

        loadInitialParams = params
        loadInitialCallback = callback
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        Timber.i("Load after, page: ${params.key}")

        loadAfterParams = params
        loadAfterCallback = callback
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun retry() {
        //If we have already called loadedAfter, we should retry loadAfter
        //Otherwise we failed onInitial and should call loadInitial
        loadAfterParams?.let { safeParams ->
            loadAfterCallback?.let { safeCallback ->
                Timber.i("Retrying request")
                loadAfter(safeParams, safeCallback)
            }
        } ?: run {
            loadInitialParams?.let { safeParams ->
                loadInitialCallback?.let { safeCallback ->
                    loadInitial(safeParams, safeCallback)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        //ignored
    }
}