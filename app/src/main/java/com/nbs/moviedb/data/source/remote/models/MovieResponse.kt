package com.nbs.moviedb.data.source.remote.models

import com.nbs.moviedb.domain.models.Movie
import com.nbs.moviedb.domain.repository.ApiConstants
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
@JsonClass(generateAdapter = true)
data class MovieResponse(
    val results: List<MovieDto>
)

@JsonClass(generateAdapter = true)
data class MovieDto(
    val id: Long,
    val title: String,
    val overview: String,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "genre_ids")
    val genreIds: List<Long>,
)

/**Mapper*/
fun MovieDto.asDomainModel(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        backdropUrl = this.backdropPath?.let { "${ApiConstants.backdropUrl}$it" } ?: "",
        posterUrl = this.posterPath?.let { "${ApiConstants.posterUrl}$it" } ?: "",
        date = this.releaseDate ?: "-",
        voteAverage = this.voteAverage,
        genres = ""
    )
}

fun List<MovieDto>.asDomainModels(): List<Movie> {
    return this.map { it.asDomainModel() }
}
