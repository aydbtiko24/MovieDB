package com.nbs.moviedb.data.source.remote.models

import com.nbs.moviedb.domain.models.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
@JsonClass(generateAdapter = true)
data class GenreDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String
)

/**Mapper*/
fun GenreDto.asDomainModel(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}

fun List<GenreDto>.asDomainModels(): List<Genre> {
    return this.map { it.asDomainModel() }
}
