package com.sample.domain.usecases

import com.sample.domain.repositories.analytics.AnalyticsRepository
import com.sample.domain.repositories.analytics.analyticsRepository
import com.sample.entities.AnalyticsEvent
import io.reactivex.Completable
import java.util.*

fun sessionStarted(
    sessionName: String,
    repository: AnalyticsRepository = analyticsRepository
): Completable {
    return repository.saveEvent(sessionName, AnalyticsEvent("session started", Date().time))
}

fun flushSession(
    sessionName: String,
    repository: AnalyticsRepository = analyticsRepository
): Completable {
    return repository.loadEvent(sessionName).flatMapCompletable { repository.flushEvent(it) }
}