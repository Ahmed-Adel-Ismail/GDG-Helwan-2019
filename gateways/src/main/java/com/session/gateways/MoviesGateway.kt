package com.session.gateways

import com.session.testing.FailedToRequestException
import com.session.testing.movies.Movies
import com.session.testing.movies.MoviesGateway

class MoviesGatewayImplementer : MoviesGateway {

    @Throws(FailedToRequestException::class)
    override suspend fun requestAllMovies(): Movies {
        TODO("Not yet implemented")
    }

    override fun saveAllMovies(movies: Movies) {
        TODO("Not yet implemented")
    }

    override fun loadAllMovies(): Movies? {
        TODO("Not yet implemented")
    }
}