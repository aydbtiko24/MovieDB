package com.nbs.moviedb.domain.usecase.movie

import com.nbs.moviedb.domain.models.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/6/2021.
 *
 */
interface GetHomeComingSoonMovies {

    operator fun invoke(year: String): Flow<List<Movie>>
}