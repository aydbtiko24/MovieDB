package com.nbs.moviedb.data.source.local

import com.google.common.truth.Truth.assertThat
import com.nbs.moviedb.domain.models.Genre
import com.nbs.moviedb.domain.models.toGenreString
import org.junit.Test

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class ListGenreToStringTest {

    @Test
    fun `get string genre contains expected value`() {
        // given
        val genres = listOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "Animation"),
        )
        // when
        val strGenre = genres.toGenreString()
        // then
        println(strGenre)
        val expected = "Action, Adventure, Animation"
        assertThat(strGenre).isEqualTo(expected)
    }

}