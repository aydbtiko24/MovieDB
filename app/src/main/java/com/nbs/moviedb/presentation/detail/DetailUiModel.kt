package com.nbs.moviedb.presentation.detail

import com.nbs.moviedb.domain.models.Cast
import com.nbs.moviedb.domain.models.DetailMovie

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
data class DetailUiModel(
    val detailMovie: DetailMovie,
    val casts: List<Cast>
)