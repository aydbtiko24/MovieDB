package com.nbs.moviedb.domain.models

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val backdropUrl: String,
    val posterUrl: String,
    val date: String,
    val voteAverage: Double,
    val genres: String
)