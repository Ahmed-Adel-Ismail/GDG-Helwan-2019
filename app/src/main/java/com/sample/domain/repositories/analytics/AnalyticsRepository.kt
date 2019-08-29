package com.sample.domain.repositories.analytics

import com.sample.entities.AnalyticsEvent
import io.reactivex.Completable
import io.reactivex.Maybe

val analyticsRepository by lazy { AnalyticsRepositoryImplementer() }

interface AnalyticsRepository {

    fun saveEvent(key: Any, event: AnalyticsEvent): Completable

    fun loadEvent(key: Any): Maybe<AnalyticsEvent>

    fun flushEvent(event: AnalyticsEvent) : Completable
}