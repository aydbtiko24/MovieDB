package com.nbs.moviedb.domain.models

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
data class Favorite(
    val id: Long,
    val movieId: Long,
    val title: String,
    val backdropUrl: String,
    val yearDate: String,
    val genres: String
)
