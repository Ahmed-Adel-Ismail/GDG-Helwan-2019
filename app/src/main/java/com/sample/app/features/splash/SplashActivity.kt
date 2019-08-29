package com.sample.app.features.splash

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sample.app.R
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        supportFragmentManager.takeIf { savedInstanceState == null }
            ?.beginTransaction()
            ?.replace(R.id.container, SplashFragment())
            ?.commitNow()
    }
}





