package com.sample.app.features.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sample.app.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class SplashFragment : Fragment() {

    private val disposables = CompositeDisposable()
    private val intents: BehaviorSubject<Intents> = BehaviorSubject.createDefault(Initialize)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposables.add(
            view(model(intents))
        )
    }

    override fun onDestroy() {
        intents.onComplete()
        disposables.clear()
        super.onDestroy()
    }
}