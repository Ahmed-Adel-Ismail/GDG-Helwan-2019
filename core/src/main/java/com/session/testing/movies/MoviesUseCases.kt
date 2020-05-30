package com.session.testing.movies

import com.session.testing.MoviesGateway
import java.lang.UnsupportedOperationException

fun showAllMovies(gateway: MoviesGateway = MoviesGateway): Movies {
    return runCatching { gateway.requestAllMovies() }
        .onSuccess { gateway.saveAllMovies(it) }
        .getOrThrow()
}

fun sortMoviesByName(movies: Movies?) = movies?.sortedBy { it.name }

fun sortMoviesByRating(movies: Movies?) =
    movies?.sortedByDescending { it.rating } ?: throw UnsupportedOperationException()