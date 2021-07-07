package com.nbs.moviedb.domain.models

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
data class DetailMovie(
    val id: Long,
    val title: String,
    val overview: String,
    val date: String,
    val backdropUrl: String,
    val genres: List<Genre>
)
