package com.session.testing.movies

import com.session.testing.MoviesGateway

suspend fun showAllMovies(gateway: MoviesGateway = MoviesGateway): Movies? {
    return runCatching { gateway.requestAllMovies() }
        .onSuccess { gateway.saveAllMovies(it) }
        .getOrNull()
}

fun sortMoviesByName(movies: Movies?) = movies?.sortedBy { it.name }

fun sortMoviesByRating(movies: Movies?) = movies?.sortedBy { it.rating }