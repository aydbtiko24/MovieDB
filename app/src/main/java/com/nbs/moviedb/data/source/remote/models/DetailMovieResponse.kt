package com.nbs.moviedb.data.source.remote.models

import com.nbs.moviedb.domain.models.DetailMovie
import com.nbs.moviedb.domain.repository.ApiConstants
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
@JsonClass(generateAdapter = true)
data class DetailMovieDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "genres")
    val genres: List<GenreDto>,
)

/**Mapper*/
fun DetailMovieDto.asDomainModel(): DetailMovie {
    return DetailMovie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        date = this.releaseDate ?: "-",
        backdropUrl = this.backdropPath?.let { "${ApiConstants.backdropUrl}$it" } ?: "",
        genres = this.genres.asDomainModels()
    )
}