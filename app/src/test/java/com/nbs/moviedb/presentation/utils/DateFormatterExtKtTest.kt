package com.nbs.moviedb.presentation.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
class DateFormatterExtKtTest {

    @Test
    fun `get year query, contain expected value`() {
        // given
        val timeInMillis = 1594054800000 // 2020/07/07
        // when
        val result = timeInMillis.toYearQuery()
        // then
        val expected = "2021"
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `get year date, contain expected value`() {
        // given
        val date = "2022-06-17"
        // when
        val result = date.toYearDate()
        // then
        val expected = "2022"
        assertThat(result).isEqualTo(expected)
    }

}