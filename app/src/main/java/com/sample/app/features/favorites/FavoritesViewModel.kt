package com.sample.app.features.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.session.testing.favorites.markAsFavorite
import com.session.testing.favorites.markAsNotFavorite
import com.session.testing.favorites.showAllFavorites
import com.session.testing.movies.Movie
import com.session.testing.movies.Movies

class FavoritesViewModel(
    val favorites: MutableLiveData<Movies> = MutableLiveData(),
    private val showAllFavoritesUseCase: () -> Movies = { showAllFavorites() },
    private val markAsFavoriteUseCase: (Movie) -> Unit = { markAsFavorite(it)},
    private val markAsNotFavoriteUseCase: (Movie) -> Unit = { markAsNotFavorite(it)}
) : ViewModel() {

    init {
        favorites.value = showAllFavoritesUseCase()
    }

    fun markFavorite(movie: Movie){
        markAsFavoriteUseCase(movie)
        favorites.value = showAllFavoritesUseCase()
    }

    fun markNotFavorite(movie: Movie){
        markAsNotFavoriteUseCase(movie)
        favorites.value = showAllFavoritesUseCase()
    }
}