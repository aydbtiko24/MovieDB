package com.nbs.moviedb.domain.usecase.favorite

import com.nbs.moviedb.domain.models.Favorite

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
interface AddFavorite {

    suspend operator fun invoke(favorite: Favorite)
}