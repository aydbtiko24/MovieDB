package com.nbs.moviedb.domain.models

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
data class Genre(
    val id: Long,
    val name: String
    )

fun List<Genre>.toGenreString(): String {
    return this.joinToString { it.name }
}