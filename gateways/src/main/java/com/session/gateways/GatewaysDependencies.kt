package com.session.gateways

import android.app.Application
import android.content.Context

internal lateinit var AppContext: Context private set

object GatewaysDependencies {
    fun with(application: Application): GatewaysDependencies {
        AppContext = application
        return this
    }
}