package com.sample.domain.gateways

import io.reactivex.Maybe
import io.reactivex.Single


val cacheGateway by lazy { CacheGateway() }

class CacheGateway {

    private val data = HashMap<Any, Any>()

    fun <T : Any> save(key: Any, value: T): Single<T> {
        return Single.fromCallable {
            data.put(key, value)
            value
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(ClassCastException::class)
    fun <T : Any> load(key: Any): Maybe<T> {
        return Maybe.defer {
            data.get(key)
                ?.let { Maybe.just(it as T) }
                ?: Maybe.empty<T>()
        }
    }

    fun <T : Any> remove(key: Any, value: T): Single<T> {
        return Single.fromCallable {
            data.remove(key)
            value
        }
    }


}