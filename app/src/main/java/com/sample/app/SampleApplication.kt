package com.sample.app

import android.app.Application
import com.session.gateways.FavoritesGatewayImplementer
import com.session.gateways.GatewaysDependencies
import com.session.gateways.MoviesGatewayImplementer
import com.session.testing.CoreDependencies

class SampleApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        GatewaysDependencies.with(this)

        CoreDependencies.moviesGateway(MoviesGatewayImplementer())
        CoreDependencies.favoritesGateway(FavoritesGatewayImplementer())
    }
}