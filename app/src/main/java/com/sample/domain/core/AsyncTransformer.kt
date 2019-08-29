package com.sample.domain.core

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class AsyncTransformer<T>(
    private val scope: CoroutineScope = GlobalScope,
    private val dispatcher: CoroutineDispatcher = IO
) : SingleTransformer<T, Deferred<T>> {


    override fun apply(upstream: Single<T>): SingleSource<Deferred<T>> {
        return Single.fromCallable {
            scope.async(dispatcher) { upstream.blockingGet() }
        }
    }
}