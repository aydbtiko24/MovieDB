package com.nbs.moviedb.presentation.detail

import com.nbs.moviedb.R

/**
 * Created by aydbtiko on 5/8/2021.
 */
data class FavoriteUiState(
    val favorite: Boolean
) {

    val textResId: Int
        get() =
            if (favorite) R.string.remove_from_favorite
            else R.string.add_to_favorite

    val iconResId: Int
        get() =
            if (favorite) R.drawable.ic_remove
            else R.drawable.ic_add

}
