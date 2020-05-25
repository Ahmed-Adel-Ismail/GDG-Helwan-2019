package com.session.testing.favorites

import com.session.testing.FavoritesGateway
import com.session.testing.MoviesGateway
import com.session.testing.movies.Movie
import com.session.testing.movies.Movies
import com.session.testing.movies.MoviesGateway

fun showAllFavorites(
    favoritesGateway: FavoritesGateway = FavoritesGateway,
    moviesGateway: MoviesGateway = MoviesGateway
): Movies {
    return favoritesGateway.loadAllFavorites()
        ?.map { it.movieId }
        ?.let { favoritesIds ->
            moviesGateway.loadAllMovies()?.filter { favoritesIds.contains(it.id) }
        }
        ?: listOf()
}

fun markAsFavorite(movie: Movie, gateway: FavoritesGateway = FavoritesGateway) {
    gateway.addToFavorites(Favorite(movie.id))
}

fun markAsNotFavorite(movie: Movie, gateway: FavoritesGateway = FavoritesGateway) {
    gateway.removeFromFavorites(Favorite(movie.id))
}