package com.session.testing.movies

import com.session.testing.FailedToRequestException
import org.junit.Assert.*
import org.junit.Test

class MoviesUseCasesKtTest {

    @Test
    fun `showAllMovies() then invoke requestAllMovies()`() {

        var invoked = false

        // arrange
        val moviesGateway = object : MoviesGateway {
            override fun requestAllMovies(): Movies {
                invoked = true
                return listOf(Movie(), Movie())
            }
        }

        // act
        showAllMovies(moviesGateway)

        // assert
        assert(invoked)

    }

    @Test
    fun `showAllMovies() with successful requestAllMovies() then invoke saveAllMovies()`() {
        var invoked = false

        // arrange
        val moviesGateway = object : MoviesGateway {
            override fun requestAllMovies(): Movies {
                return listOf(Movie(), Movie())
            }

            override fun saveAllMovies(movies: Movies) {
                invoked = true
                super.saveAllMovies(movies)
            }
        }

        // act
        showAllMovies(moviesGateway)

        // assert
        assert(invoked)
    }

    @Test
    fun `showAllMovies() with successful requestAllMovies() then return movies list`() {

        // arrange
        val moviesGateway = object : MoviesGateway {
            override fun requestAllMovies(): Movies {
                return listOf(Movie(), Movie())
            }
        }

        // act
        val result = showAllMovies(moviesGateway)

        // assert
        val expected = listOf(Movie(), Movie())
        assertEquals(expected, result)
    }

    @Test
    fun `showAllMovies() with failing requestAllMovies() then never invoke saveAllMovies()`() {
        var invoked = false

        // arrange
        val moviesGateway = object : MoviesGateway {
            override fun requestAllMovies(): Movies {
                throw FailedToRequestException()
            }

            override fun saveAllMovies(movies: Movies) {
                invoked = true
                super.saveAllMovies(movies)
            }
        }

        // act
        try {
            showAllMovies(moviesGateway)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        // assert
        assertFalse(invoked)
    }

    @Test(expected = FailedToRequestException::class)
    fun `showAllMovies() with failing requestAllMovies() then throw FailedToRequestException`() {

        // arrange
        val moviesGateway = object : MoviesGateway {
            override fun requestAllMovies(): Movies {
                throw FailedToRequestException()
            }
        }

        // act
        showAllMovies(moviesGateway)
    }


    @Test
    fun `sortMoviesByName() with non null movies then return movies list sorted by name`() {

        // arrange
        val movies = listOf(
            Movie(name = "b"),
            Movie(name = "c"),
            Movie(name = "a")
        )

        // act
        val result = sortMoviesByName(movies)

        // assert
        val expected = listOf(Movie(name = "a"), Movie(name = "b"), Movie(name = "c"))
        assertEquals(expected, result)

    }

    @Test
    fun `sortMoviesByName() with null movies then return null`() {

        // arrange
        val movies: Movies? = null

        // act
        val result = sortMoviesByName(movies)

        // assert
        assertNull(result)

    }

    @Test
    fun `sortMoviesByRating() with non null movies then return movies list sorted by rating descending`() {

        // arrange
        val movies = listOf(
            Movie(rating = 0.5),
            Movie(rating = 0.1),
            Movie(rating = 0.7)
        )

        // act
        val result = sortMoviesByRating(movies)

        // assert
        val expected = listOf(
            Movie(rating = 0.7),
            Movie(rating = 0.5),
            Movie(rating = 0.1)
        )

        assertEquals(expected, result)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `sortMoviesByRating() with null movies then throw UnsupportedOperationException`() {

        // arrange
        val movies: Movies? = null

        // act
        sortMoviesByRating(movies)

    }


}