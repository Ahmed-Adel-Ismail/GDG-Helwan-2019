package com.sample.app.features.splash

import android.annotation.SuppressLint
import com.sample.domain.usecases.isLoggedInUser
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


@SuppressLint("CheckResult")
fun model(intents: BehaviorSubject<Intents>): BehaviorSubject<ViewState> {

    val viewStates = BehaviorSubject.create<ViewState>()

    intents.observeOn(Schedulers.io()).subscribeBy {

        when (it) {
            is Initialize -> waitThenFinishInitialization(viewStates)
        }

    }
    return viewStates
}

@SuppressLint("CheckResult")
private fun waitThenFinishInitialization(viewStates: BehaviorSubject<ViewState>) {

    viewStates.onNext(OnInitializeStarted)

    Observable.interval(2, TimeUnit.SECONDS).firstElement().subscribeBy {

        isLoggedInUser()
            .map { OnInitializeFinished(it) }
            .blockingGet()
            .also { viewStates.onNext(it) }

    }
}
