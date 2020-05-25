package com.session.testing.favorites

import com.session.testing.movies.Movie
import com.session.testing.movies.MoviesGateway
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoritesUseCasesKtTest {

    @Test
    fun `showAllFavorites() with saved favorites then return favorite movies`() {

        val moviesGateway = object : MoviesGateway {
            override fun loadAllMovies() = listOf(Movie(id = 1), Movie(id = 2), Movie(id = 3))
        }

        val favoritesGateway = object : FavoritesGateway {
            override fun loadAllFavorites() = listOf(Favorite(movieId = 2), Favorite(movieId = 3))
        }

        val result = showAllFavorites(
            moviesGateway = moviesGateway,
            favoritesGateway = favoritesGateway
        )

        val expected = listOf(Movie(id = 2), Movie(id = 3))
        assertEquals(expected, result)
    }

    @Test
    fun `showAllFavorites() with no saved favorites then return empty list`() {

    }

    @Test
    fun `showAllFavorites() with no saved favorites then never invoke loadAllMovies()`() {

    }

    @Test
    fun `markAsFavorite() with non favorite movie then invoke addToFavorites()`() {

    }

    @Test
    fun `markAsFavorite() with an exiting favorite movie then never invoke addToFavorites()`() {

    }


}