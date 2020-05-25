package com.session.testing

import com.session.testing.favorites.FavoritesGateway
import com.session.testing.movies.MoviesGateway


internal lateinit var MoviesGateway : MoviesGateway private set
internal lateinit var FavoritesGateway : FavoritesGateway private set

object CoreDependencies {

    fun moviesGateway(moviesGateway: MoviesGateway) : CoreDependencies {
        MoviesGateway = moviesGateway
        return this
    }

    fun favoritesGateway(favoritesGateway: FavoritesGateway) : CoreDependencies {
        FavoritesGateway = favoritesGateway
        return this
    }

}