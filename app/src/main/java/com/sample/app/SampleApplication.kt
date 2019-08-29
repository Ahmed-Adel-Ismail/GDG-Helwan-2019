package com.sample.app

import android.app.Application
import com.sample.domain.DomainIntegration
import com.sample.domain.usecases.flushSession
import com.sample.domain.usecases.sessionStarted
import io.reactivex.schedulers.Schedulers

private const val SESSION_APP = "SESSION_APP"

class SampleApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        DomainIntegration.with(this)

        sessionStarted(SESSION_APP)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnError(Throwable::printStackTrace)
            .onErrorComplete()
            .subscribe()

    }

    /**
     * this is invoked on Emulators only
     */
    override fun onTerminate() {

        flushSession(SESSION_APP)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnError(Throwable::printStackTrace)
            .onErrorComplete()
            .subscribe()

        super.onTerminate()
    }
}