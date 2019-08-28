package com.sample.app.features.splash

import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_splash.*


fun SplashFragment.view(viewStates: BehaviorSubject<ViewState>): Disposable =
    viewStates.observeOn(AndroidSchedulers.mainThread()).subscribeBy {
        when (it) {
            is OnInitializeStarted -> showProgress()
            is OnInitializeFinished -> hideProgressAndNavigate()
        }
    }

private fun SplashFragment.hideProgressAndNavigate() {
    progressBar.visibility = View.GONE
}

private fun SplashFragment.showProgress() {
    progressBar.visibility = View.VISIBLE
}
