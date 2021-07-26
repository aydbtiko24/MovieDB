package com.nbs.moviedb.presentation.utils

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
data class ErrorState(
    val message: String = "",
    val visible: Boolean
){
    companion object{
        val Initial = ErrorState(visible = false)
    }
}
