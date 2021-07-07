package com.nbs.moviedb.domain.usecase.favorite

import com.nbs.moviedb.domain.models.Favorite
import kotlinx.coroutines.flow.Flow

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
interface GetFavorites {

    operator fun invoke(searchQuery: String): Flow<List<Favorite>>
}
