package com.nbs.moviedb.presentation.favorite

import com.nbs.moviedb.data.source.local.asDomainModel
import com.nbs.moviedb.data.source.local.favoriteEntities
import com.nbs.moviedb.domain.repository.FavoriteRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
@ExperimentalCoroutinesApi
class AddFavoriteImplTest {

    private lateinit var addFavorite: AddFavoriteImpl

    @MockK
    lateinit var favoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addFavorite = AddFavoriteImpl(
            favoriteRepository = favoriteRepository
        )
    }

    @Test
    fun `add favorite invoked on repository`() = runBlockingTest {
        // given
        val favorite = favoriteEntities[1].asDomainModel()
        coEvery { favoriteRepository.addFavorite(favorite) } coAnswers {
            println("invoked on repository")
        }
        // when
        addFavorite(favorite)
        // then
        coVerify { favoriteRepository.addFavorite(favorite) }
    }
}
