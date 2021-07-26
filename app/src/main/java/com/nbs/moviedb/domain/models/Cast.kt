package com.nbs.moviedb.domain.models

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
data class Cast(
    val id: Long,
    val name: String,
    val knownDepartment: String,
    val imgUrl: String
)
