package com.session.testing.favorites

import java.io.Serializable

typealias Favorites = List<Favorite>

data class Favorite(
    val movieId: Long? = null
) : Serializable

interface FavoritesGateway {
    fun loadAllFavorites() : Favorites?
    fun addToFavorites(favorite: Favorite)
    fun removeFromFavorites(favorite: Favorite)
}