package com.nbs.moviedb.data.source.remote.models

import com.nbs.moviedb.domain.models.Cast
import com.nbs.moviedb.domain.repository.ApiConstants
import com.squareup.moshi.Json

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
data class CastResponse(
    @Json(name = "cast")
    val casts: List<CastDto>
)

data class CastDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "known_for_department")
    val knownDepartment: String,
    @Json(name = "profile_path")
    val imagePath: String?
)

/**Mapper*/
fun CastDto.asDomainModel(): Cast {
    return Cast(
        id = this.id,
        name = this.name,
        knownDepartment = this.knownDepartment,
        imgUrl = this.imagePath?.let { "${ApiConstants.posterUrl}$it" } ?: "",
    )
}

fun List<CastDto>.asDomainModels(): List<Cast> {
    return this.map { it.asDomainModel() }
}
