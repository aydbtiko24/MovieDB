package com.nbs.moviedb.domain.repository

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
object ApiConstants {

    /** base url path*/
    const val BASE_URL = "https://api.themoviedb.org/3/"

    /** url query*/
    const val keyQuery = "api_key"
    const val languageQuery = "language"
    const val pageQuery = "page"
    const val sortByQuery = "sort_by"
    const val includeAdultQuery = "include_adult"
    const val includeVideoQuery = "include_video"
    const val yearQuery = "year"

    /**default url query value*/
    const val pageQueryValue = 1
    const val pageQueryPopularValue = 2
    const val sortByQueryValue = "popularity.desc"
    const val includeAdultQueryValue = false
    const val includeVideoQueryValue = false
    const val languageQueryValue = "en-US"

    /** url path*/
    const val movieIdPath = "movie_id"
    const val discoverPath = "discover/movie"
    const val detailPath = "movie/{$movieIdPath}"

    /** image url*/
    const val backdropUrl = "https://image.tmdb.org/t/p/w780"
    const val posterUrl = "https://image.tmdb.org/t/p/w185"

}
