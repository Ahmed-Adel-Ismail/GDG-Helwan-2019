package com.session.testing.movies

import com.session.testing.FailedToRequestException
import java.io.Serializable

typealias Movies = List<Movie>

data class Movie(
    val id: Long? = null,
    val name: String? = null,
    val rating: Double? = null,
    val imageUrl: String? = null
) : Serializable

interface MoviesGateway {
    @Throws(FailedToRequestException::class)
    suspend fun requestAllMovies() : Movies
    fun saveAllMovies(movies: Movies)
    fun loadAllMovies() : Movies?
}