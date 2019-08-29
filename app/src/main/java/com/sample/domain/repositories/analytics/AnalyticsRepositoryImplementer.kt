package com.sample.domain.repositories.analytics

import com.sample.domain.gateways.CacheGateway
import com.sample.domain.gateways.cacheGateway
import com.sample.entities.AnalyticsEvent
import io.reactivex.Completable
import io.reactivex.Maybe

class AnalyticsRepositoryImplementer(
    private val cache: CacheGateway = cacheGateway
) : AnalyticsRepository {

    override fun saveEvent(key: Any, event: AnalyticsEvent): Completable {
        return cache.save(key, event).ignoreElement()
    }

    override fun loadEvent(key: Any): Maybe<AnalyticsEvent> {
        return cache.load(key)
    }

    override fun flushEvent(event: AnalyticsEvent): Completable {
        return Completable.fromAction{
            // simulate server request / response
            Thread.sleep(2000)

        }
    }
}