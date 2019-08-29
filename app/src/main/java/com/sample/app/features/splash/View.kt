package com.sample.app.features.splash

import android.content.Intent
import android.view.View
import com.sample.app.features.home.HomeActivity
import com.sample.app.features.registration.RegistrationActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_splash.*


fun SplashFragment.view(viewStates: BehaviorSubject<ViewState>): Disposable =
    viewStates.observeOn(AndroidSchedulers.mainThread()).subscribeBy {
        when (it) {
            is OnInitializeStarted -> showProgress()
            is OnInitializeFinished -> hideProgressAndNavigate(it.loggedInUser)
        }
    }

private fun SplashFragment.hideProgressAndNavigate(loggedInUser: Boolean) {

    progressBar.visibility = View.GONE

    if (loggedInUser) startActivity(Intent(context, HomeActivity::class.java))
    else startActivity(Intent(context, RegistrationActivity::class.java))

    activity?.finish()


}

private fun SplashFragment.showProgress() {
    progressBar.visibility = View.VISIBLE
}
