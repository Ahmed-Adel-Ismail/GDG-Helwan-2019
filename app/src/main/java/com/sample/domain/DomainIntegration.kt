package com.sample.domain

import android.app.Application
import java.lang.ref.WeakReference


object DomainIntegration {

    private lateinit var applicationReference : WeakReference<Application>

    fun with(application: Application){
        applicationReference = WeakReference(application)
    }

    fun getApplication() = applicationReference.get()!!
}